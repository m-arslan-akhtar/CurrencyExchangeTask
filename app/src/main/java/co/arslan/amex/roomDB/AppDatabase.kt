package co.arslan.amex.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.arslan.amex.models.ConversionRates
import co.arslan.amex.utils.ConversionRatesTypeConverter
import co.arslan.amex.dao.AppDao

@Database(entities = [ConversionRates::class], version = 1, exportSchema = false)
@TypeConverters(ConversionRatesTypeConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun appDao(): AppDao
}

private lateinit var INSTANCE : AppDatabase

fun getDatabase(context: Context): AppDatabase {

    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "currencies_database"
            ).build()
        }
    }

    return INSTANCE
}