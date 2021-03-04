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
import com.nurbk.ps.countryweather.databinding.ItemWeatherHourBinding
import com.nurbk.ps.countryweather.model.weather.daysweather.ListItem
import com.nurbk.ps.countryweather.model.weather.fivedayweather.ItemHourly
import com.nurbk.ps.countryweather.utils.AppUtil
import com.nurbk.ps.countryweather.utils.ConstanceString
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var type = 1


    inner class WeatherViewHolder(val mBinding: ItemWeatherHourBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(
            item: ItemHourly, position: Int,
        ) {
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
            mBinding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    inner class DaysWeatherViewHolder(val mBinding: ItemWeatherDayBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(
            item: ListItem,
        ) {

            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.timeInMillis = item.dt * 1000L
            if (AppUtil.isRTL(mBinding.root.context)) {
                mBinding.dayNameTextView.text =
                    ConstanceString.DAYS_OF_WEEK_PERSIAN[calendar[Calendar.DAY_OF_WEEK] - 1]
            } else {
                mBinding.dayNameTextView.text =
                    ConstanceString.DAYS_OF_WEEK[calendar[Calendar.DAY_OF_WEEK] - 1]
            }
            if (item.temp!!.max < 0 && item.temp!!.max > -0.5) {
                item.temp!!.max = 0.0
            }
            if (item.temp!!.min < 0 && item.temp!!.min > -0.5) {
                item.temp!!.min = 0.0
            }
            if (item.deg < 0 && item.deg > -0.5) {
                item.deg = 0
            }
            mBinding.dateTextView.text = java.lang.String.format(
                Locale.getDefault(), "%s %d",
                ConstanceString.MONTH_NAME[calendar[Calendar.MONTH]],
                calendar[Calendar.DAY_OF_MONTH]
            )
            mBinding.weatherImageView.setAnimation(
                AppUtil.getWeatherAnimation(
                    item.weather!![0].id
                )
            )
            mBinding.weatherImageView.playAnimation()
            mBinding.minTempTextView.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                item.temp!!.min
            )
            mBinding.maxTempTextView.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                item.temp!!.max
            )

            mBinding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1)
            return WeatherViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_weather_hour,
                    parent,
                    false
                )
            )
        return DaysWeatherViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_weather_day,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeatherViewHolder)
            holder.bind(
                data[position], position
            )
        else if (holder is DaysWeatherViewHolder)
            holder.bind(
                dataDay[position]
            )
    }

    override fun getItemCount() = if (type == 1) data.size else dataDay.size


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
    private val diffCallbackDay = object : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    val differDay = AsyncListDiffer(this, diffCallbackDay)

    var dataDay: List<ListItem>
        get() = differDay.currentList
        set(value) = differDay.submitList(value)

    override fun getItemViewType(position: Int): Int {
        if (type == 1)
            return 1
        return 0
    }

    private var onItemClickListener: ((Any) -> Unit)? = null
    fun setItemClickListener(listener: (Any) -> Unit) {
        onItemClickListener = listener
    }

}



