package com.nurbk.ps.countryweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ItemCitiesBinding
import com.nurbk.ps.countryweather.databinding.ItemImageBinding
import com.nurbk.ps.countryweather.model.ObjectDetails
import com.nurbk.ps.countryweather.model.cities.City
import com.nurbk.ps.countryweather.model.photos.Photo

class ItemParentDetailsAdapter constructor(
//     val glide: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = ObjectDetails("", "", arrayListOf(), -1)

    inner class ItemCitiesViewHolder(val item: ItemCitiesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(city: City) {
            item.item = city
        }
    }

    inner class ItemImageViewHolder(val item: ItemImageBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(photos: Photo) {
            Glide.with(item.root.context).load(getUrl(photos)).into(item.itemImage)
        }

        private fun getUrl(photo: Photo): String {
            return "https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_w.jpg"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return ItemImageViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_image, parent, false
                )
            )
        } else {
            return ItemCitiesViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_cities, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemImageViewHolder) {
            holder.bind(data.data[position] as Photo)
        } else if (holder is ItemCitiesViewHolder) {
            holder.bind(data.data[position] as City)
        }
    }

    override fun getItemCount() = data.data.size


    override fun getItemViewType(position: Int): Int {
        if (data.type == 1) {
            return 1
        } else if (data.type == 2) {
            return 2
        }
        return super.getItemViewType(position)
    }

}