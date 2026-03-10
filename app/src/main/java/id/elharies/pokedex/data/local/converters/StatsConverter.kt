package id.elharies.pokedex.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.elharies.pokedex.data.remote.dto.StatsResponse

class StatsConverter {
    @TypeConverter
    fun fromStatsList(stats: List<StatsResponse>): String = Gson().toJson(stats)

    @TypeConverter
    fun toStatsList(json: String): List<StatsResponse> =
        Gson().fromJson(json, object : TypeToken<List<StatsResponse>>() {}.type)
}