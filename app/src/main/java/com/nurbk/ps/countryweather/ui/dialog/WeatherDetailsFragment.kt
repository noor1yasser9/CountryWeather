package com.nurbk.ps.countryweather.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.adapters.WeatherAdapter
import com.nurbk.ps.countryweather.databinding.FragmentDetailsHoursBinding
import com.nurbk.ps.countryweather.model.weather.fivedayweather.FiveDayResponse
import com.nurbk.ps.countryweather.model.weather.fivedayweather.ItemHourly
import com.nurbk.ps.countryweather.ui.viewmodel.WeatherViewModel
import com.nurbk.ps.countryweather.utils.AppUtil
import com.nurbk.ps.countryweather.utils.ConstanceString
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint

class WeatherDetailsFragment() :
    BottomSheetDialogFragment() {

    private lateinit var mBinding: FragmentDetailsHoursBinding
    private val viewModel: WeatherViewModel by viewModels()

    private val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter()
    }

    var onChange: OnStateChangedDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentDetailsHoursBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        weatherAdapter.type = 0
        return mBinding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        requireArguments().getParcelable<FiveDayResponse>(ConstanceString.DATA_DETAILS)?.let {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            mBinding.dayNameTextView.text =
                ConstanceString.DAYS_OF_WEEK[calendar[Calendar.DAY_OF_WEEK] - 1]
            it.list!!.subList(0, 6).forEach { item ->
                if (item.main!!.tempMax < 0 && item.main!!.tempMax > -0.5) {
                    item.main!!.tempMax = 0.0
                }
                if (item.main!!.tempMin < 0 && item.main!!.tempMin > -0.5) {
                    item.main!!.tempMin = 0.0
                }
                if (item.main!!.temp < 0 && item.main!!.temp > -0.5) {
                    item.main!!.temp = 0.0
                }
                mBinding.tempTextView.text = java.lang.String.format(Locale.getDefault(),
                    "%.0f°",
                    item.main!!.temp)
                mBinding.minTempTextView.text = java.lang.String.format(Locale.getDefault(),
                    "%.0f°",
                    item.main!!.temp)
                mBinding.maxTempTextView.text = java.lang.String.format(Locale.getDefault(),
                    "%.0f°",
                    item.main!!.tempMax)
            }

            mBinding.animationView.setAnimation(AppUtil.getWeatherAnimation(it.list!![0].weather!![0].id))
            mBinding.animationView.playAnimation()

            setChartValues(it.list!!)
        }

    }


    private fun setChartValues(itemHourlyDBList: List<ItemHourly>) {
        val entries: MutableList<Entry> = ArrayList<Entry>()

        for ((i, itemHourlyDB) in itemHourlyDBList.subList(0, 6).withIndex()) {
            entries.add(Entry(i.toFloat(), itemHourlyDB.main!!.temp.toFloat()))
        }
        val dataSet = LineDataSet(entries, "Label") // add entries to dataset
        dataSet.lineWidth = 4f
        dataSet.circleRadius = 7f
        dataSet.isHighlightEnabled = false
        dataSet.setCircleColor(Color.parseColor("#33b5e5"))
        dataSet.valueTextSize = 12F
        dataSet.valueTextColor = Color.WHITE
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format(Locale.getDefault(), "%.0f", value)
            }
        }
        val lineData = LineData(dataSet)
        mBinding.chart.description.isEnabled = false
        mBinding.chart.axisLeft.setDrawLabels(false)
        mBinding.chart.axisRight.setDrawLabels(false)
        mBinding.chart.xAxis.setDrawLabels(false)
        mBinding.chart.legend.isEnabled = false // Hide the legend
        mBinding.chart.xAxis.setDrawGridLines(false)
        mBinding.chart.axisLeft.setDrawGridLines(false)
        mBinding.chart.axisRight.setDrawGridLines(false)
        mBinding.chart.axisLeft.setDrawAxisLine(false)
        mBinding.chart.axisRight.setDrawAxisLine(false)
        mBinding.chart.xAxis.setDrawAxisLine(false)
        mBinding.chart.setScaleEnabled(false)
        mBinding.chart.data = lineData
        mBinding.chart.animateY(1000)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val d = super.onCreateDialog(savedInstanceState)
        d.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById(R.id.design_bottom_sheet) as FrameLayout?
            val behaviour: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        // Bottom Sheet was dismissed by user! But this is only fired, if dialog is swiped down! Not if touch outside dismissed the dialog or the back button
                        dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val slide = bottomSheet.width * slideOffset
                    onChange?.let {
                        it.onChanged(slide)
                    }
                }
            })
        }
        return d
    }

    interface OnStateChangedDialog {
        fun onChanged(setOff: Float)
    }
}