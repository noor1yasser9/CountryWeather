package com.nurbk.ps.countryweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ItemWeatherDayBinding
import com.nurbk.ps.countryweather.model.weather.fivedayweather.ItemHourly
import com.nurbk.ps.countryweather.utils.AppUtil
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

//    private lateinit var colorss: IntArray
//    private lateinit var colorsAlpha: IntArray
//


    inner class WeatherViewHolder(val mBinding: ItemWeatherDayBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(
            item: ItemHourly, position: Int
//                 ,color:Int,colorsAlpha:Int
        ) {
//            mBinding.cardView.setCardBackgroundColor(color)
//            val colors = intArrayOf(
//                Color.TRANSPARENT,
//                R.color.material_blue,
//                Color.TRANSPARENT
//            )
//            val calendar = Calendar.getInstance(TimeZone.getDefault())
//            calendar.timeInMillis = item.dt * 1000L
//            if (AppUtil.isRTL(mBinding.root.context)) {
//                mBinding.dayNameTextView.text = ConstanceString.DAYS_OF_WEEK_PERSIAN[calendar[Calendar.DAY_OF_WEEK] - 1]
//            } else {
//                mBinding.dayNameTextView.text = ConstanceString.DAYS_OF_WEEK[calendar[Calendar.DAY_OF_WEEK] - 1]
//            }
//            if (item.main!!.tempMax < 0 && item.main!!.tempMax > -0.5) {
//                item.main!!.tempMax = 0.0
//            }
//            if (item.main!!.tempMin < 0 && item.main!!.tempMin > -0.5) {
//                item.main!!.tempMin = 0.0
//            }
//            if (item.main!!.temp < 0 && item.main!!.temp > -0.5) {
//                item.main!!.temp = 0.0
//            }
//            mBinding.tempTextView.text = String.format(
//                Locale.getDefault(),
//                "%.0f°",
//                item.main!!.temp
//            )
//            mBinding.minTempTextView.text = String.format(
//                Locale.getDefault(),
//                "%.0f°",
//                item.main!!.tempMin
//            )
//            mBinding.maxTempTextView.text = String.format(
//                Locale.getDefault(),
//                "%.0f°",
//                item.main!!.tempMax
//            )

//
//            val shape = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors)
//            shape.shape = GradientDrawable.OVAL
//            mBinding.shadowView.background = shape
            if (position == 2) {
                mBinding.view.isGone = true
            }
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.timeInMillis = item.dt * 1000L
            if (item.main!!.temp < 0 && item.main!!.temp > -0.5) {
                item.main!!.temp = (0.0)
            }
            mBinding.timeTextView.text = AppUtil.getTime(calendar, mBinding.root.context)
            mBinding.tempTextView.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                item.main!!.temp
            )
            mBinding.weatherImageView.setAnimation(
                AppUtil.getWeatherAnimation(
                    item.weather!![0].id
                )
            )
            mBinding.weatherImageView.playAnimation()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_weather_day,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(
            data[position], position
//            ,colorss[position],colorsAlpha[position]
        )
    }

    override fun getItemCount() = data.size


    private val diffCallback = object : DiffUtil.ItemCallback<ItemHourly>() {
        override fun areItemsTheSame(oldItem: ItemHourly, newItem: ItemHourly): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: ItemHourly, newItem: ItemHourly): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    val differ = AsyncListDiffer(this, diffCallback)

    var data: List<ItemHourly>
        get() = differ.currentList
        set(value) = differ.submitList(value)

}