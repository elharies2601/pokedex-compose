package id.elharies.pokedex.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.elharies.pokedex.R
import id.elharies.pokedex.ui.theme.Grey
import id.elharies.pokedex.ui.theme.SemiGrey

@Composable
fun CirclePokemonPlaceholder(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp
) {
    Box(
        modifier = modifier
            .background(color = SemiGrey, shape = CircleShape)
            .size(size),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painterResource(R.drawable.ic_pokeball_grey),
            contentDescription = "circle",
            modifier = Modifier.size(size / 2),
        )
    }
}

@Preview(name = "CirclePokemonPlaceholder")
@Composable
private fun PreviewCirclePokemonPlaceholder() {
    CirclePokemonPlaceholder()
}