package id.elharies.pokedex.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.elharies.pokedex.R

@Composable
fun HeaderGeneralSection(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.title_login)
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_pokedex),
            fontSize = 44.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Icon(
            painterResource(R.drawable.ic_pokeball_grey),
            contentDescription = "image poke ball",
            tint = Color.Transparent
        )
    }
}

@Preview(name = "HeaderGeneralSection")
@Composable
private fun PreviewHeaderGeneralSection() {
    HeaderGeneralSection()
}