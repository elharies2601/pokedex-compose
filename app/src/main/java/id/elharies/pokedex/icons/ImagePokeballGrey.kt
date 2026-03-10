package id.elharies.pokedex.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ImagePokeBallGrey: ImageVector
    get() {
        if (_pokeballGrey != null) return _pokeballGrey!!

        _pokeballGrey = ImageVector.Builder(
            name = "pokeballGrey",
            defaultWidth = 654.dp,
            defaultHeight = 487.dp,
            viewportWidth = 654f,
            viewportHeight = 487f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
            }
            group {
                path(
                    fill = SolidColor(Color(0xFFF4F6F9)),
                    pathFillType = PathFillType.EvenOdd
                ) {
                }
            }
        }.build()

        return _pokeballGrey!!
    }

private var _pokeballGrey: ImageVector? = null