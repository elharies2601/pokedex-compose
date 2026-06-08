package id.elharies.pokedex.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.elharies.pokedex.component.EmptyScreen
import id.elharies.pokedex.component.ItemPokemon
import id.elharies.pokedex.ui.theme.WhiteBone

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onDetail: (id: Long) -> Unit = {}
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(HomeIntent.InitData)
    }

    HomeContent(uiState = uiState) {
        if (it is HomeIntent.GoToDetail) {
            onDetail(it.id)
            return@HomeContent
        }
        viewModel.onAction(it)
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState = HomeUiState(),
    onAction: (HomeIntent) -> Unit = {}
) {
    var query by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyGridState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            
            lastVisibleItem >= totalItems - 3 && totalItems > 0
        }
    }

    LaunchedEffect(shouldLoadMore, uiState.pokemons.size, uiState.hasReachedEnd) {
        if (shouldLoadMore && !uiState.isLoadingMore && !uiState.hasReachedEnd && uiState.pokemons.isNotEmpty()) {
            onAction(HomeIntent.LoadMore)
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = WhiteBone) {
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            stickyHeader() {
                OutlinedTextField(
                    query,
                    onValueChange = {
                        query = it
                        if (it.isEmpty()) {
                            onAction(HomeIntent.SearchPoke(it))
                        }
                    },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { onAction(HomeIntent.SearchPoke(query)) }) {
                            Icon(Icons.Default.Search, contentDescription = "icon search")
                        }
                    },
                    placeholder = {
                        Text("Cari Pokemon...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = Color.White, 
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            when {
                uiState.isLoading -> {
                    items(count = 6) {
                        ItemPokemon(modifier = Modifier.height(200.dp), isLoading = true)
                    }
                }
                uiState.errorMessage != null -> {
                    item(key = "error", span = { GridItemSpan(maxLineSpan) }) {
                        EmptyScreen(
                            modifier = Modifier.fillMaxSize(), 
                            message = uiState.errorMessage
                        ) {
                            onAction(HomeIntent.InitData)
                        }
                    }
                }
                uiState.pokemons.isEmpty() -> {
                    item(key = "item not found", span = { GridItemSpan(maxLineSpan) }) {
                        EmptyScreen(modifier = Modifier.fillMaxSize())
                    }
                }
                else -> {
                    items(count = uiState.pokemons.size, key = { index -> uiState.pokemons[index].pokemonName }) { index ->
                        val pokemon = uiState.pokemons[index]
                        ItemPokemon(pokemon = pokemon) {
                            onAction(HomeIntent.GoToDetail(pokemon.id))
                        }
                    }
                    
                    if (uiState.isLoadingMore) {
                        items(count = 2) {
                            ItemPokemon(modifier = Modifier.height(200.dp), isLoading = true)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeContent() {
    HomeContent()
}