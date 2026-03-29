package com.insa.mygameslist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.insa.mygameslist.domain.usecase.GetGamesUseCase
import com.insa.mygameslist.domain.usecase.SearchGamesUseCase

class MainViewModelFactory(
    private val getGamesUseCase: GetGamesUseCase, private val searchGamesUseCase: SearchGamesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MainViewModel(
                getGamesUseCase,
                searchGamesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
