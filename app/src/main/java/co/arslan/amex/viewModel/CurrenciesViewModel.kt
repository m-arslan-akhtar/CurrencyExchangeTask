package co.arslan.amex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.arslan.amex.MainApplication
import co.arslan.amex.models.ConversionRates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrenciesViewModel  : ViewModel() {

    val db = MainApplication.database.appDao()
    var liveData: LiveData<ConversionRates> = db.getAllDataSet()


    fun insertData(model: ConversionRates) {
        CoroutineScope(Dispatchers.Default).launch {
            db.insertItems(model)//appDao().insertItems(model)
        }
    }

    // add livedata here then listen to other side
    fun getData(): LiveData<ConversionRates> {
        return db.getAllDataSet()
    }

     fun getExchangeRate(amount: Double, exchangeRateFrom: Double,  exchangeRateTo: Double): Double? {

        // Convert From Value to PKR first
        val amountInPKR = amount / exchangeRateFrom

        // Convert PKR to Desired Value
        return amountInPKR * exchangeRateTo

    }


}