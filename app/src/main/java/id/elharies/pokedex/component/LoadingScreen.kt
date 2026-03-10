package id.elharies.pokedex.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.elharies.pokedex.ui.theme.LightTeal

@Composable
fun LoadingDialog(modifier: Modifier = Modifier, isShow: Boolean = false) {
    if (isShow) {
        AlertDialog(modifier = modifier.size(200.dp), onDismissRequest = {}, title = {}, text = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp), strokeWidth = 3.dp, color = LightTeal)
            }
        }, confirmButton = {}, containerColor = Color.White)
    }
}

@Preview(name = "LoadingDialog")
@Composable
private fun PreviewLoadingDialog() {
    LoadingDialog()
}