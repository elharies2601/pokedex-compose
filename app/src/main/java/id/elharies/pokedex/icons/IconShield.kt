package id.elharies.pokedex.icons

/*
Font Awesome Free License
-------------------------

Font Awesome Free is free, open source, and GPL friendly. You can use it for
commercial projects, open source projects, or really almost whatever you want.
Full Font Awesome Free license: https://fontawesome.com/license/free.

# Icons: CC BY 4.0 License (https://creativecommons.org/licenses/by/4.0/)
In the Font Awesome Free download, the CC BY 4.0 license applies to all icons
packaged as SVG and JS file types.

# Fonts: SIL OFL 1.1 License (https://scripts.sil.org/OFL)
In the Font Awesome Free download, the SIL OFL license applies to all icons
packaged as web and desktop font files.

# Code: MIT License (https://opensource.org/licenses/MIT)
In the Font Awesome Free download, the MIT license applies to all non-font and
non-icon files.

# Attribution
Attribution is required by MIT, SIL OFL, and CC BY licenses. Downloaded Font
Awesome Free files already contain embedded comments with sufficient
attribution, so you shouldn't need to do anything additional when using these
files normally.

We've kept attribution comments terse, so we ask that you do not actively work
to remove them from files, especially code. They're a great way for folks to
learn about Font Awesome.

# Brand Icons
All brand icons are trademarks of their respective owners. The use of these
trademarks does not indicate endorsement of the trademark holder by Font
Awesome, nor vice versa. **Please do not use brand logos for any purpose except
to represent the company, product, or service to which they refer.**

*/
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconShield: ImageVector
    get() {
        if (_IconShield != null) return _IconShield!!

        _IconShield = ImageVector.Builder(
            name = "shield-alt",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 512f,
            viewportHeight = 512f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(466.5f, 83.7f)
                lineToRelative(-192f, -80f)
                arcToRelative(48.15f, 48.15f, 0f, false, false, -36.9f, 0f)
                lineToRelative(-192f, 80f)
                curveTo(27.7f, 91.1f, 16f, 108.6f, 16f, 128f)
                curveToRelative(0f, 198.5f, 114.5f, 335.7f, 221.5f, 380.3f)
                curveToRelative(11.8f, 4.9f, 25.1f, 4.9f, 36.9f, 0f)
                curveTo(360.1f, 472.6f, 496f, 349.3f, 496f, 128f)
                curveToRelative(0f, -19.4f, -11.7f, -36.9f, -29.5f, -44.3f)
                close()
                moveTo(256.1f, 446.3f)
                lineToRelative(-0.1f, -381f)
                lineToRelative(175.9f, 73.3f)
                curveToRelative(-3.3f, 151.4f, -82.1f, 261.1f, -175.8f, 307.7f)
                close()
            }
        }.build()

        return _IconShield!!
    }

private var _IconShield: ImageVector? = null

