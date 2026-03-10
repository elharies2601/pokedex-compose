package id.elharies.pokedex.util

import androidx.compose.ui.graphics.Color
import id.elharies.pokedex.ui.theme.Beige
import id.elharies.pokedex.ui.theme.Black
import id.elharies.pokedex.ui.theme.DarkBrown
import id.elharies.pokedex.ui.theme.Grey
import id.elharies.pokedex.ui.theme.LightBlue
import id.elharies.pokedex.ui.theme.LightBrown
import id.elharies.pokedex.ui.theme.LightCyan
import id.elharies.pokedex.ui.theme.LightGreen
import id.elharies.pokedex.ui.theme.LightPink
import id.elharies.pokedex.ui.theme.LightPurple
import id.elharies.pokedex.ui.theme.LightRed
import id.elharies.pokedex.ui.theme.LightTeal
import id.elharies.pokedex.ui.theme.LightYellow
import id.elharies.pokedex.ui.theme.Lilac
import id.elharies.pokedex.ui.theme.Pink
import id.elharies.pokedex.ui.theme.Purple
import id.elharies.pokedex.ui.theme.Red
import id.elharies.pokedex.ui.theme.Violet
import java.util.Locale

enum class ColorPokemon(val typeName: String, val color: Color) {
    Grass("grass", LightGreen),
    Poison("poison", LightPurple),
    Fire("fire", LightRed),
    Flying("flying", Lilac),
    Water("water", LightBlue),
    Bug("bug", LightTeal),
    Normal("normal", Beige),
    Electric("electric", LightYellow),
    Ground("ground", DarkBrown),
    Fairy("fairy", Pink),
    Fighting("fighting", Red),
    Psychic("psychic", LightPink),
    Rock("rock", LightBrown),
    Steel("steel", Grey),
    Ice("ice", LightCyan),
    Ghost("ghost", Purple),
    Dragon("dragon", Violet),
    Dark("dark", Black),
    Monster("monster", LightBlue),
    Unknown("unknown", LightBlue);
}