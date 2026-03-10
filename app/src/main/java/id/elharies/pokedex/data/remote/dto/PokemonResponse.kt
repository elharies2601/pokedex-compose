package id.elharies.pokedex.data.remote.dto


import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("next")
    val next: String? = "",
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: List<PokemonItem> = listOf()
) {
    data class PokemonItem(
        @SerializedName("name")
        val name: String = "",
        @SerializedName("url")
        val url: String = ""
    )
}