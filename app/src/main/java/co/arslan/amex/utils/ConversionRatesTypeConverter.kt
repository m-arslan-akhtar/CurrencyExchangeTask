package co.arslan.amex.utils

import androidx.room.TypeConverter
import co.arslan.amex.models.ConversionRates
import com.google.gson.GsonBuilder

class ConversionRatesTypeConverter {

    val gson = GsonBuilder().setLenient().create()


    @TypeConverter
    fun fromJson(json: String): ConversionRates {
        return gson.fromJson(json, ConversionRates::class.java)
    }

    @TypeConverter
    fun toJson(conversionRates: ConversionRates): String {
        return gson.toJson(conversionRates)
    }
}