# MyGamesList - Projet Programmation Mobile

- **Corentin PITON *(4INFO)***
- **Mélissa BOKO *(3INFO)***

Les consignes du projet ont été déplacées dans le
fichier [consignes_README.md](./consignes_README.md)

---

## Présentation du Projet

Le projet implémente les fonctionnalités décrites dans les TP obligatoires (listage des jeux,
second écran, barre de recherche, favoris).

## Pré-requis

Pour faire fonctionner l'application, il faut tout d'abord renseigner vos identifiants dans [/data/fetchApi/ApiService](./app/src/main/java/com/insa/mygameslist/data/fetchApi/ApiService.kt)

```kotlin
interface ApiService {
    @Headers(
        "Client-ID: [client-id]",
        "Authorization: Bearer [access-token]",
        "Content-Type: application/json",
    )
    @POST("games")
    suspend fun getGames(@Body query: RequestBody): Response<List<GameDTO>>
}
````

## Fonctionnalités Bonus réalisées

* Les jeux récupérés et listés sont obtenus via l'API IGDB (*TP facultatif*)

    * Pour cela, les jeux sont récupérés via des requêtes HTTP POST avec en body :

      ```kotlin
      "fields id, first_release_date, name, summary, total_rating, genres.name, cover.url, platforms.name, platforms.platform_logo.url; limit $pageSize; offset ${pageSize * page};"
      ```

      Les `genres.name`, `cover.url`, `platforms.name` et `platforms.platform_logo.url` permettent
      d’effectuer les jointures avec les autres tables de l'API.

    * Un système de pagination a été mis en place : chaque page contient 100 jeux.

    * La recherche de jeux est réalisée via des requêtes HTTP grâce au body :

      ```kotlin
      "where name ~ *\"$query\"* | genres.name ~ *\"$query\"* | platforms.name ~ *\"$query\"*;"
      ```

      L'attribut `search` de IGDB ne permet que de chercher dans le nom (le `~` permet d'être
      *case-insensitive*).

* Un scroll infini a été réalisé : quand l'utilisateur approche de la fin de la liste de jeux, une
  requête est envoyée à l'API pour récupérer la suite des jeux.

* *Clean Architecture* : une "clean architecture" a été réalisée après le dernier TP, la structure
  du projet est la suivante :

    * `domain/` : Logique métier (modèle utilisé par l'UI, interface pour le repository implémenté
      dans `data/` et utilisé par l'UI, et des "use cases" permettant d'appeler les méthodes du
      repository (classes avec `invoke` pour être appelées comme des fonctions par l'UI)).

    * `data/` : Sources de données (API, repository, DTO).

        * `GameComplete` : data class permettant de regrouper les données des DTO
        * `DataSource` : interface implémentée par `IGDBApi` et `IGDBLocal` permettant de récupérer
          les données (selon une page, ou permettant la recherche textuelle)
        * `mapper` : contient une extension/fonction de `GameComplete` pour transformer un
          `GameComplete` en objet `Game` de `domain/` (partagé avec l'UI)
        * `GameRepositoryImpl` : est une implémentation de l'interface de repository de `domain/`
          (permet d'appeler les méthodes d'une `DataSource` choisie)

    * `ui/` : Éléments graphiques (screens, ViewModels, thèmes, composants).

        * `screens/` : Les différents écrans (page d'accueil avec tous les jeux et page de
          détails d'un jeu)
        * `components/` : des composants (parties de la page d'accueil et de la page de détails),
          mais contient des previews (`@Preview`)
        * `viewmodel/` : contient le ViewModel du `MainActivity` et sa factory associée (la factory
          permet de créer le ViewModel à partir des "use cases" : fonction de récupération des jeux
          et de recherche).
          *Exemple de l'initialisation par la factory avec l'API d'IGDB*

          ```kotlin
          private val viewModel: MainViewModel by viewModels {
            val repository = GameRepositoryImpl(applicationContext, IGDBApi())
            MainViewModelFactory(
                GetGamesUseCase(repository), SearchGamesUseCase(repository)
            )
          }
          ```

## Utilisation de l'IA

L'IA (*ChatGPT/Gemini*) n'a pas été utilisée dans le projet lors des TP obligatoires, ni pour la
récupération des données via l'API d'IGDB.

Mais elle a été utilisée pour aider à réaliser la clean architecture : indiquer quelle structure le
projet doit avoir : `domain/`, `data/`, `ui/` et comment fonctionnent les ViewModels et les autres
composants (quand faire passer en paramètres les use cases / repository / data sources / etc.).
Aussi, cela a permis d’indiquer comment faire communiquer les ViewModels avec l'UI (utilisation
des `MutableStateFlow` et `StateFlow` que l'UI lit et affiche ; cela aurait pu être fait avec des
callbacks aussi).

Enfin, elle a aidé à réaliser le scroll infini (la fonction pour appeler automatiquement la
DataSource quand on arrive vers la fin) ainsi que le thème / la mise en page de l'écran de détails
du jeu (plutôt que d'avoir juste un fond rose fuchsia).

