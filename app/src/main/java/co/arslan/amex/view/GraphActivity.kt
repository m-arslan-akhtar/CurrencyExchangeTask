package co.arslan.amex.view

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import co.arslan.amex.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class GraphActivity : AppCompatActivity() {

    private var chart: LineChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        chart = findViewById(R.id.chart)
        val backButton = findViewById<ImageView>(R.id.back_image)

        // Dummy Data for Chart
        setData()

        backButton.setOnClickListener{
            finish()
        }
    }


    private fun setData() {


        val xAxisValues = ArrayList<String>()
        xAxisValues.add("Term1")
        xAxisValues.add("Term2")
        xAxisValues.add("Term3")
        xAxisValues.add("Term4")
        xAxisValues.add("Term5")
        xAxisValues.add("Term6")

        chart?.xAxis?.valueFormatter = IndexAxisValueFormatter(xAxisValues)


        val entries = ArrayList<Entry>()

        entries.add(Entry(0F, 60F)) //20    2018f  (2018f, 20))
        entries.add(Entry(1F, 57F)) //40    2019f   (2019f, 40))
        entries.add(Entry(2F, 65F)) //60    2020f (2020f, 60))
        entries.add(Entry(3F, 70F)) //80    2021f (2021f, 80))
        entries.add(Entry(4F, 80F)) //80    2021f (2021f, 80))
        entries.add(Entry(5F, 70F)) //80    2021f (2021f, 80))


        val entry = ArrayList<Entry>()
        entry.add(Entry(0F, 70F))
        entry.add(Entry(1F, 50F))
        entry.add(Entry(2F, 60F))
        entry.add(Entry(3F, 65F))
        entry.add(Entry(4F, 75F))
        entry.add(Entry(5F, 80F))

        val aed = ArrayList<Entry>()
        aed.add(Entry(0F, 80F))
        aed.add(Entry(1F, 70F))
        aed.add(Entry(2F, 50F))
        aed.add(Entry(3F, 68F))
        aed.add(Entry(4F, 55F))
        aed.add(Entry(5F, 36F))


        val lines = ArrayList<LineDataSet>()

        val set1 = LineDataSet(entries, "USD")
        set1.setDrawFilled(true)
        set1.fillColor = Color.WHITE
        set1.color = Color.RED
        set1.setCircleColor(Color.DKGRAY)
        lines.add(set1)

        val set2 = LineDataSet(entry, "PKR")
        set2.setDrawFilled(true)
        set2.fillColor = Color.WHITE
        set2.color = Color.BLUE
        set2.setCircleColor(Color.RED)
        lines.add(set2)

        val set3 = LineDataSet(aed, "AED")
        set3.setDrawFilled(true)
        set3.fillColor = Color.WHITE
        set3.color = Color.YELLOW
        set3.setCircleColor(Color.parseColor("#8B0000"))
        lines.add(set3)


        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        dataSets.add(set2)
        dataSets.add(set3)

        chart?.data = LineData(dataSets)

        chart?.description?.text = ""

        chart?.description?.textColor = Color.RED


        chart?.animateY(1400, Easing.EaseInOutBounce)
    }

}