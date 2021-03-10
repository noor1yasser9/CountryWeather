package com.nurbk.ps.countryweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ItemCountriesBinding
import com.nurbk.ps.countryweather.model.countries.CountriesPageItem
import javax.inject.Inject


class CountriesAdapter
@Inject
constructor(
    val glide: RequestManager,
) :
    RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {
    inner class CountriesViewHolder(val item: ItemCountriesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(countriesItem: CountriesPageItem) {
//            if (countriesItem.name == ("Israel")) {
//                countriesItem.name = "Palestine"
//                countriesItem.flag = "https://restcountries.eu/data/pse.svg"
//            }
            item.txtName.setSingleLine()
            item.txtName.isSelected = true
            item.countriesItem = countriesItem
            glide.load(countriesItem.countryInfo.flag).into(item.imageView)

            item.root.setOnClickListener {
                onItemClickListener?.let {
                    it(countriesItem)
                }
            }
        }
    }


    var countriesList: List<CountriesPageItem> = ArrayList()

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


    private var onItemClickListener: ((CountriesPageItem) -> Unit)? = null

    fun setItemClickListener(listener: (CountriesPageItem) -> Unit) {
        onItemClickListener = listener
    }


}