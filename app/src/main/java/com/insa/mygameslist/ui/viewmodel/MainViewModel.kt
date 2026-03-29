package com.insa.mygameslist.ui.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insa.mygameslist.domain.model.Game
import com.insa.mygameslist.domain.usecase.GetGamesUseCase
import com.insa.mygameslist.domain.usecase.SearchGamesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MainViewModel(
    private val getGamesUseCase: GetGamesUseCase, private val searchGamesUseCase: SearchGamesUseCase
) : ViewModel() {

    // state flow pour avoir la liste des jeux en calcul arrière plan
    private val _games = MutableStateFlow<Map<Long, Game>>(emptyMap())
    val games: StateFlow<Map<Long, Game>> = _games.asStateFlow()

    // state du texte de la recherche
    val searchText = TextFieldState("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // message d'erreur du loadGames
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var currentPage = 0
    private var isLastPage = false

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredGames: StateFlow<List<Game>> =
        snapshotFlow { searchText.text }.debounce(500) // attend que l'utilisateur fasse une pause quand il écrit
            .flatMapLatest { query ->
                val queryStr = query.toString()
                if (queryStr.isBlank()) {
                    // Si pas de recherche, on observe recupere la liste complete
                    _games.map { it.values.toList() }
                } else {
                    flow {
                        _isLoading.value = true
                        try {
                            val results = searchGamesUseCase(queryStr)
                            _games.value += results.associateBy { it.id }
                            emit(results)
                        } catch (e: Exception) {
                            _errorMessage.value = "Erreur de recherche : ${e.localizedMessage}"
                            emit(emptyList())
                        } finally {
                            _isLoading.value = false
                        }
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun loadGames(page: Int = 0) {
        if (_isLoading.value || (page > 0 && isLastPage)) return

        // corountine (scope du viewModel)
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val newGames = getGamesUseCase(page)
                if (newGames.isEmpty()) {
                    isLastPage = true
                } else {
                    _games.value += newGames.associateBy { it.id }
                    currentPage = page
                }
            } catch (e: Exception) {
                _errorMessage.value = "Une erreur est survenue : ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        // On ne charge la page suivante que si on n'est pas en train de filtrer
        // (car le filtrage est local sur les données déjà chargées)
        if (searchText.text.isBlank()) {
            loadGames(currentPage + 1)
        }
    }
}
