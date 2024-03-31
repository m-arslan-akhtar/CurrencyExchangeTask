package co.arslan.amex.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import co.arslan.amex.models.ConversionRates
import co.arslan.amex.viewModel.CurrenciesViewModel
import co.arslan.amex.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private var currenciesObject : ConversionRates? = null
    val exchangeRatesList = ArrayList<Pair<String, Double>>()
    private var fromAdapter : ArrayAdapter<String>? = null
    private var toAdapter : ArrayAdapter<String>? = null
    var fromValue = 0.0
    var toValue = 0.0
    var toCurrency = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // INIT Views
        val fromSpinner = findViewById<Spinner>(R.id.sp_from_currency)
        val toSpinner = findViewById<Spinner>(R.id.sp_to_currency)
        val amountValue = findViewById<EditText>(R.id.et_amount)
        val convert = findViewById<Button>(R.id.bt_convert)
        val graph = findViewById<Button>(R.id.bt_graph)
        val totalValue = findViewById<TextView>(R.id.tv_exchanged_value)

        // ViewModel
        val viewModel = ViewModelProvider(this)[CurrenciesViewModel::class.java]
        viewModel.liveData.observe(this) {
            currenciesObject = it
            it?.let {
                extractCurrencyRates(it)

                fromAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, exchangeRatesList.map { it.first }
                )
                fromSpinner.adapter = fromAdapter

                toAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, exchangeRatesList.map { it.first }
                )
                toSpinner.adapter = toAdapter
            }

        }



        // From Spinner
        if (fromSpinner != null) {

            fromSpinner.adapter = fromAdapter

            fromSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    fromValue = exchangeRatesList[position].second.toDouble()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }


        // To Spinner
        if (toSpinner != null) {

            toSpinner.adapter = toAdapter

            toSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    toValue = exchangeRatesList[position].second.toDouble()
                    toCurrency = exchangeRatesList[position].first.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }


        // Click listeners
        graph.setOnClickListener {
            startActivity(Intent(this, GraphActivity::class.java))
        }
        convert.setOnClickListener {
            val convertedAmount = amountValue.text.toString()
            if (fromValue != 0.0 && toValue != 0.0 && convertedAmount.isNotEmpty()){
                val result = viewModel.getExchangeRate(convertedAmount.toDouble(), fromValue, toValue)
                val txt = roundOffDecimal(result).toString()  + " "+ toCurrency
                totalValue.text = txt
            }else{
                Toast.makeText(this, "Incomplete data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun roundOffDecimal(number: Double?): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
        fun extractCurrencyRates(conversionRates: ConversionRates) {
        // Get the properties of the ConversionRates class
        val properties = conversionRates.javaClass.declaredFields

        // Iterate over the properties and extract their names
        for (property in properties) {
            if (property.name != "id") {
                property.isAccessible = true
                if (property.type == Double::class.java || property.type == java.lang.Double::class.java) {
                    val currencyName = property.name.uppercase(Locale.ROOT) // Get the currency name
                    val currencyValue = property.get(conversionRates) as Double // Get the currency value
                    exchangeRatesList.add(currencyName to currencyValue) // Add name-value pair to the list
                } else if (property.type == Int::class.java || property.type == java.lang.Integer::class.java) {
                    val currencyName = property.name.uppercase(Locale.ROOT) // Get the currency name
                    val currencyValue = (property.get(conversionRates) as Int).toDouble() // Convert Integer to Double
                    exchangeRatesList.add(currencyName to currencyValue) // Add name-value pair to the list
                }


            }

        }
    }

}