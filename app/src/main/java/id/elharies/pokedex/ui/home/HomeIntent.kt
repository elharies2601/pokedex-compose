package id.elharies.pokedex.ui.home

sealed interface HomeIntent {
    data class SearchPoke(val name: String?): HomeIntent
    data object InitData: HomeIntent
    data class GoToDetail(val id: Long): HomeIntent
}