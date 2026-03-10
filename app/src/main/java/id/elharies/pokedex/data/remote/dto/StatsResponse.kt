package id.elharies.pokedex.data.remote.dto


import com.google.gson.annotations.SerializedName

data class StatsResponse(
    @SerializedName("base_stat")
    val baseStat: Int = 0,
    @SerializedName("effort")
    val effort: Int = 0,
    @SerializedName("stat")
    val stat: Stat = Stat()
) {
    data class Stat(
        @SerializedName("name")
        val name: String = "",
        @SerializedName("url")
        val url: String = ""
    )
}