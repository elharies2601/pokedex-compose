package id.elharies.pokedex.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.elharies.elutility.compose.ext.shimmerEffect
import id.elharies.pokedex.R
import id.elharies.pokedex.domain.model.Pokemon
import id.elharies.pokedex.ui.theme.BlackBackground
import id.elharies.pokedex.ui.theme.White

@Composable
fun ItemPokemon(
    modifier: Modifier = Modifier,
    pokemon: Pokemon = Pokemon(),
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(6.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors().copy(White)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.pokemon_id_format, pokemon.id),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = BlackBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (isLoading) {
                            Modifier.shimmerEffect()
                        } else {
                            Modifier
                        }
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            ImagePokemon(url = pokemon.imageUrl, modifier = if (isLoading) Modifier.shimmerEffect() else Modifier)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pokemon.pokemonName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BlackBackground,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().then(
                    if (isLoading) {
                        Modifier.shimmerEffect()
                    } else {
                        Modifier
                    }
                )
            )
        }
    }
}

@Preview(name = "ItemPokemon")
@Composable
private fun PreviewItemPokemon() {
    ItemPokemon(pokemon = Pokemon())
}