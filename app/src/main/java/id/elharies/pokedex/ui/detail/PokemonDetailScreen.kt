package id.elharies.pokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.elharies.elutility.compose.component.LoadingScreen
import id.elharies.pokedex.R
import id.elharies.pokedex.component.ImagePokemon
import id.elharies.pokedex.component.LoadingDialog
import id.elharies.pokedex.domain.model.DetailPokemon
import id.elharies.pokedex.domain.model.PokemonStat
import id.elharies.pokedex.icons.IconHealthCross
import id.elharies.pokedex.icons.IconRunning
import id.elharies.pokedex.icons.IconShield
import id.elharies.pokedex.icons.IconSword
import id.elharies.pokedex.ui.theme.Black
import id.elharies.pokedex.ui.theme.BlackBackground
import id.elharies.pokedex.ui.theme.Blue
import id.elharies.pokedex.ui.theme.Indigo
import id.elharies.pokedex.ui.theme.LightGreen
import id.elharies.pokedex.ui.theme.LightYellow
import id.elharies.pokedex.ui.theme.PokedexTheme
import id.elharies.pokedex.ui.theme.Red
import id.elharies.pokedex.util.ColorPokemon

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    id: Long = 0,
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonDetail(id)
    }

    PokemonDetailContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetry = { viewModel.retry(id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailContent(
    uiState: PokemonDetailUiState = PokemonDetailUiState(),
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier.size(24.dp),
                            tint = if (uiState.errorType != null) Black else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = if (uiState.errorType != null) {
            Color.White
        } else {
            val typeName = uiState.detailPokemon.types.firstOrNull() ?: "unknown"
            ColorPokemon.entries.find { it.typeName == typeName }?.color ?: Color.White
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(), start = paddingValues.calculateStartPadding(
                        LayoutDirection.Ltr
                    ), end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                )
        ) {
            when {
                uiState.errorType == DetailErrorType.NoConnection -> {
                    OfflineErrorState(
                        errorMessage = uiState.errorMessage ?: stringResource(R.string.no_connection_message),
                        onRetry = onRetry,
                        onBack = onBackClick
                    )
                }
                uiState.errorType != null -> {
                    NotFoundErrorState(
                        errorMessage = uiState.errorMessage ?: stringResource(R.string.general_error),
                        onBack = onBackClick
                    )
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        PokemonInfo(
                            name = uiState.detailPokemon.name,
                            id = uiState.detailPokemon.id,
                            types = uiState.detailPokemon.types
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            PokemonImagePlaceholder(
                                modifier = Modifier.size(240.dp),
                                url = uiState.detailPokemon.imageUrl
                            )
                        }
                        DetailContent(pokemon = uiState.detailPokemon)
                    }
                }
            }
        }
    }

    LoadingDialog(isShow = uiState.isLoading)
}

@Composable
private fun OfflineErrorState(
    errorMessage: String,
    onRetry: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_wifi_off),
            contentDescription = stringResource(R.string.no_connection),
            modifier = Modifier.size(120.dp),
            tint = Color.Gray.copy(alpha = 0.4f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = stringResource(R.string.no_connection),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = errorMessage,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 300.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGreen
            )
        ) {
            Text(
                text = stringResource(R.string.retry),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        TextButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 300.dp)
        ) {
            Text(
                text = stringResource(R.string.back_to_list),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun NotFoundErrorState(
    errorMessage: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pokeball_grey),
            contentDescription = stringResource(R.string.not_found),
            modifier = Modifier
                .size(120.dp)
                .alpha(0.3f),
            tint = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = stringResource(R.string.pokemon_tidak_ditemukan),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = errorMessage,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 300.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGreen
            )
        ) {
            Text(
                text = stringResource(R.string.back_to_list),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun PokemonInfo(
    name: String,
    id: Long,
    types: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name.lowercase(),
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.pokemon_id_format, id),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            types.forEach { type ->
                TypeChip(type = type)
            }
        }
    }
}

@Composable
private fun TypeChip(
    type: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.White.copy(alpha = 0.2f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = type,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun PokemonImagePlaceholder(modifier: Modifier = Modifier, url: String = "") {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Decorative background circle
        Box(
            modifier = Modifier
                .size(210.dp)
                .alpha(0.12f)
                .background(Color.White, shape = CircleShape)
        )
        ImagePokemon(url = url)
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    pokemon: DetailPokemon = DetailPokemon()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                DetailPhysics(
                    title = stringResource(R.string.weight),
                    value = "${pokemon.weight} Kg"
                )
                DetailPhysics(
                    title = stringResource(R.string.height),
                    value = "${pokemon.height} M"
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.base_stats),
                fontSize = 17.sp,
                color = BlackBackground,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                ItemStats(
                    iconStat = IconHealthCross,
                    colorIcon = Red,
                    value = pokemon.hp.toString()
                )
                ItemStats(
                    iconStat = IconSword,
                    colorIcon = LightYellow,
                    value = pokemon.attack.toString()
                )
                ItemStats(
                    iconStat = IconShield,
                    colorIcon = Blue,
                    value = pokemon.defense.toString()
                )
                ItemStats(
                    iconStat = IconRunning,
                    colorIcon = Indigo,
                    value = pokemon.speed.toString()
                )
            }
        }
    }
}

@Composable
private fun DetailPhysics(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = ""
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = value, fontSize = 24.sp, color = Black, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            color = BlackBackground,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun ItemStats(
    modifier: Modifier = Modifier,
    iconStat: ImageVector = IconHealthCross,
    colorIcon: Color = Red,
    value: String = ""
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(iconStat, contentDescription = "icon stats", tint = colorIcon, modifier = Modifier.size(25.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = value, fontSize = 24.sp, color = Black, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
private fun PreviewItemStats() {
    ItemStats()
}

@Preview
@Composable
private fun PreviewDetailPhysics() {
    DetailPhysics(title = "Weight", value = "90 Kg")
}

@Preview(showBackground = true)
@Composable
private fun PokemonDetailContentPreview() {
    val detail = DetailPokemon(
        id = 1,
        name = "bulbasaur",
        imageUrl = "",
        baseExperience = 100,
        weight = 10.0,
        height = 10.0,
        stats = listOf(PokemonStat(name = "attack", baseStat = 35)),
        types = listOf("bug")
    )
    PokedexTheme {
        PokemonDetailContent(uiState = PokemonDetailUiState(detailPokemon = detail))
    }
}
