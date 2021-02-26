package com.nurbk.ps.countryweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
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

    inner class WeatherViewHolder(val mBinding: ItemWeatherDayBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(item: ItemHourly) {
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
            AppUtil.setWeatherIcon(
                mBinding.root.context,
                mBinding.weatherImageView,
                item.weather!![0].id
            )
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
        holder.bind(data[position])
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