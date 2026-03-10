package id.elharies.pokedex.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import id.elharies.pokedex.data.remote.dto.TypeResponse

class TypesConverter {
    @TypeConverter
    fun fromTypesList(types: List<TypeResponse>): String {
        val gson = Gson()
        return gson.toJson(types)
    }

    @TypeConverter
    fun toTypesList(json: String): List<TypeResponse> {
        return Gson().fromJson(json, Array<TypeResponse>::class.java).toList()

    }
}