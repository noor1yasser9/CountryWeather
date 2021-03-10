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
import com.nurbk.ps.countryweather.model.countries.Currency
import com.nurbk.ps.countryweather.model.countries.Language
import com.nurbk.ps.countryweather.model.photos.Photo

class ItemParentDetailsAdapter  constructor(

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = ObjectDetails("", "", arrayListOf(), -1)

    inner class ItemCitiesViewHolder(val item: ItemCitiesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(city: City) {
            item.item = city
        }
    }

    inner class ItemBordersViewHolder(val item: ItemCitiesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(text: String) {
            item.itemBorder = text
            item.chip4.text = text
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
        when (viewType) {
            1 -> {
                return ItemImageViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_image, parent, false
                    )
                )
            }
            2 -> {
                return ItemCitiesViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_cities, parent, false
                    )
                )
            }
            else -> {
                return ItemBordersViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_cities, parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemImageViewHolder -> {
                holder.bind(data.data[position] as Photo)
            }
            is ItemCitiesViewHolder -> {
                holder.bind(data.data[position] as City)
            }
            is ItemBordersViewHolder -> {
                when {
                    data.data[position] is Currency -> try {
                        holder.bind((data.data[position] as Currency).symbol)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    data.data[position] is Language -> holder.bind((data.data[position] as Language).name)
                    else -> holder.bind(data.data[position].toString())
                }
            }
        }
    }

    override fun getItemCount() = data.data.size


    override fun getItemViewType(position: Int): Int {
        if (data.type == 1) {
            return 1
        } else if (data.type == 2) {
            return 2
        }
        return 3
    }

}