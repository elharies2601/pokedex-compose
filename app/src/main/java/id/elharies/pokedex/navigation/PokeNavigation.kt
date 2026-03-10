package id.elharies.pokedex.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import id.elharies.pokedex.ui.detail.PokemonDetailScreen
import id.elharies.pokedex.ui.detail.PokemonDetailViewModel
import id.elharies.pokedex.ui.login.LoginScreen
import id.elharies.pokedex.ui.login.LoginViewModel
import id.elharies.pokedex.ui.main.MainScreen
import id.elharies.pokedex.ui.register.RegisterScreen
import id.elharies.pokedex.ui.register.RegisterViewModel

@Composable
fun PokeNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(PokeScreen.Login)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<PokeScreen.Login>{
                val viewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(viewModel = viewModel, onNavToHome = {
                    backStack.clear()
                    backStack.add(PokeScreen.Main)
                }, onNavToRegister =  {
                    backStack.add(PokeScreen.Register)
                })
            }
            entry<PokeScreen.Register>{
                val viewModel = hiltViewModel<RegisterViewModel>()
                RegisterScreen(viewModel = viewModel, onBack = { backStack.removeLastOrNull() })
            }
            entry<PokeScreen.Main> {
                MainScreen(openDetailPage = {
                    backStack.add(PokeScreen.Detail(it))
                }, onLogout = {
                    backStack.clear()
                    backStack.add(PokeScreen.Login)
                })
            }
            entry<PokeScreen.Home> {}
            entry<PokeScreen.Profile> {}
            entry<PokeScreen.Detail> {
                val viewModel = hiltViewModel<PokemonDetailViewModel>()
                PokemonDetailScreen(viewModel = viewModel, id = it.id) {
                    backStack.removeLastOrNull()
                }
            }
        }
    )
}