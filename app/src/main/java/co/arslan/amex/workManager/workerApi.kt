package co.arslan.amex.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import co.arslan.amex.MainApplication
import co.arslan.amex.api.CurrenciesApi
import co.arslan.amex.retrofitHelper.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class WorkerApi (context : Context, params : WorkerParameters): Worker(context,params) {

    override fun doWork(): Result {
        return try {
            val quotesApi = RetrofitHelper.getInstance().create(CurrenciesApi::class.java)
            // launching a new coroutine
            runBlocking {
                val apiDeferred = CoroutineScope(Dispatchers.Default).async {
                    val result = quotesApi.getQuotes()
                    if (result.isSuccessful) {
                        MainApplication.database.appDao()
                            .insertItems(result.body()?.conversionRates!!)
                    } else {
                        Timber.d("NO Data")
                    }
                }
                apiDeferred.await()
            }

            Result.success()
        } catch (throwable: Throwable) {
            Timber.e(throwable, "Error Fetching Data")
            Result.failure()
        }
    }
}