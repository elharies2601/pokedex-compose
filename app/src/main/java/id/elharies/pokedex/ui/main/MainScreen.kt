package id.elharies.pokedex.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import id.elharies.pokedex.ui.home.HomeScreen
import id.elharies.pokedex.ui.home.HomeViewModel
import id.elharies.pokedex.ui.profile.ProfileScreen
import id.elharies.pokedex.ui.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    openDetailPage: (Long) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val homeViewModel = hiltViewModel<HomeViewModel>()

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(
                start = it.calculateStartPadding(
                    LayoutDirection.Ltr
                ),
                end = it.calculateEndPadding(LayoutDirection.Ltr),
                top = it.calculateTopPadding()
            )
        ) {
            PrimaryTabRow(
                selectedTabIndex = selectedTab
            ) {
                Tab(selected = selectedTab == 0, onClick = {
                    selectedTab = 0
                }) {
                    Text(text = "Home")
                }
                Tab(selected = selectedTab == 1, onClick = {
                    selectedTab = 1
                }) {
                    Text(text = "Profil")
                }
            }
            when (selectedTab) {
                0 -> HomeScreen(viewModel = homeViewModel, onDetail = openDetailPage)
                1 -> ProfileScreen(profileViewModel, onLogout = onLogout)
            }
        }
    }
}

@Preview(name = "MainScreen")
@Composable
private fun PreviewMainScreen() {
    MainScreen()
}