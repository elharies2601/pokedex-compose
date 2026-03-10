package id.elharies.pokedex.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ImagePokemon(
    modifier: Modifier = Modifier,
    url: String = "",
    size: Int? = null
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .apply {
            if (size != null) {
                size(size)
            } else {
                size(Size.ORIGINAL)
            }
        }
        .crossfade(true)
        .build()

//    val painter = rememberAsyncImagePainter(model = model)

    Box(modifier, contentAlignment = Alignment.Center) {
        SubcomposeAsyncImage(
            model = model,
            contentDescription = "image pokemon",
            contentScale = ContentScale.Inside
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Error -> {
                    CirclePokemonPlaceholder(size = 150.dp)
                }
                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
                else -> {}
            }
        }
    }
}

@Preview(name = "ImagePokemon")
@Composable
private fun PreviewImagePokemon() {
    ImagePokemon()
}