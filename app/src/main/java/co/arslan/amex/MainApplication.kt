package co.arslan.amex

import android.app.Application
import androidx.room.Room
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import co.arslan.amex.roomDB.AppDatabase
import co.arslan.amex.workManager.WorkerApi
import java.util.concurrent.TimeUnit

class MainApplication: Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        val myWork = PeriodicWorkRequestBuilder<WorkerApi>(
            1, TimeUnit.DAYS)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "CurrencyWorkManager",
        ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
        myWork)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "currencies_database"
        ).build()
    }
}