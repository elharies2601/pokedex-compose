package id.elharies.pokedex.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import id.elharies.elutility.compose.component.LoadMoreIndicator
import id.elharies.elutility.compose.ext.shimmerEffect
import id.elharies.pokedex.component.EmptyScreen
import id.elharies.pokedex.component.ItemPokemon
import id.elharies.pokedex.component.LoadingDialog
import id.elharies.pokedex.domain.model.Pokemon
import id.elharies.pokedex.ui.theme.WhiteBone
import id.elharies.pokedex.util.items
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onDetail: (id: Long) -> Unit = {}
) {
    val items = viewModel.dataPokemons.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.onAction(HomeIntent.InitData)
    }

    HomeContent(items = items) {
        if (it is HomeIntent.GoToDetail) {
            onDetail(it.id)
            return@HomeContent
        }
        viewModel.onAction(it)
    }
}

@Composable
private fun HomeContent(
    items: LazyPagingItems<Pokemon> = flowOf(PagingData.empty<Pokemon>()).collectAsLazyPagingItems(),
    onAction: (HomeIntent) -> Unit = {}
) {
    var query by remember {
        mutableStateOf("")
    }

    Surface(modifier = Modifier.fillMaxSize(), color = WhiteBone) {
        LazyVerticalGrid(
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
//                        Icon(Icons.Default.Search, contentDescription = "icon search")
                    },
                    placeholder = {
                        Text("Cari Pokemon...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors().copy(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                )
            }

            when(items.loadState.refresh) {
                is LoadState.Error -> {
                    item(key = "error", span = { GridItemSpan(maxLineSpan) }) {
                        val temp =items.loadState.refresh as LoadState.Error
                        val msg = temp.error.message
                        EmptyScreen(modifier = Modifier.fillMaxSize(), message = msg ?: "Terjadi Kesalahan") {
                            items.retry()
                        }
                    }
                }
                is LoadState.Loading -> {
                    items(count = 6) {
                        ItemPokemon(modifier = Modifier.height(200.dp), isLoading = true)
                    }
                }
                is LoadState.NotLoading -> {
                    if (items.itemCount == 0) {
                        item(key = "item not found", span = { GridItemSpan(maxLineSpan) }) {
                            EmptyScreen(modifier = Modifier.fillMaxSize(),)
                        }
                    } else {
                        items(items = items, key = { item -> item.pokemonName }) {
                            val item = it ?: return@items
                            ItemPokemon(pokemon = item) {
                                onAction(HomeIntent.GoToDetail(item.id))
                            }
                        }
                    }
                }
            }

            when(items.loadState.append) {
                is LoadState.Error -> {
                    item(key = "error append", span = { GridItemSpan(maxLineSpan) }) {
                        LoadMoreIndicator(isLoading = false, showError = true) { }
                    }
                }
                LoadState.Loading -> {
                    items(count = 2) {
                        ItemPokemon(modifier = Modifier.height(200.dp), isLoading = true)
                    }
                }
                else -> {}
            }
        }

//        LoadingDialog(isShow = items.loadState.refresh == LoadState.Loading)
    }
}

@Preview
@Composable
private fun PreviewHomeContent() {
    HomeContent()
}