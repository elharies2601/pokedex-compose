package id.elharies.pokedex.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TypeResponse(
    @SerializedName("slot")
    val slot: Int = 0,
    @SerializedName("type")
    val type: Type = Type()
) {
    data class Type(
        @SerializedName("name")
        val name: String = "",
        @SerializedName("url")
        val url: String = ""
    )
}