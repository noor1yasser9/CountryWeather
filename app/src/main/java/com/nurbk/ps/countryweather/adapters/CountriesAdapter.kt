package com.nurbk.ps.countryweather.adapters

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.caverock.androidsvg.SVG
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ItemCountriesBinding
import com.nurbk.ps.countryweather.model.countries.CountriesItem
import java.io.InputStream
import javax.inject.Inject


class CountriesAdapter @Inject constructor(
    val glid: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>
) :
    RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {
    inner class CountriesViewHolder(val item: ItemCountriesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(countriesItem: CountriesItem) {

            if (countriesItem.name == ("Israel")) {
                countriesItem.name = "Palestine"
                countriesItem.flag = "https://restcountries.eu/data/pse.svg"
            }
            item.txtName.setSingleLine()
            item.txtName.isSelected = true
            glid.diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(countriesItem.flag))
                .into(item.imageView);
            item.countriesItem = countriesItem

            item.root.setOnClickListener {
                onItemClickListener?.let {
                    it(countriesItem)
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CountriesItem>() {
        override fun areItemsTheSame(oldItem: CountriesItem, newItem: CountriesItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CountriesItem, newItem: CountriesItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    val differ = AsyncListDiffer(this, diffCallback)

    var countriesList: List<CountriesItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        return CountriesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_countries, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {

        holder.bind(countriesList[position])
    }

    override fun getItemCount() = countriesList.size


    private var onItemClickListener: ((CountriesItem) -> Unit)? = null

    fun setItemClickListener(listener: (CountriesItem) -> Unit) {
        onItemClickListener = listener
    }


}