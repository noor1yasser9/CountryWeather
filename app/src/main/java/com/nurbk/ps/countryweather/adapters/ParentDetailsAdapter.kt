package com.nurbk.ps.countryweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ItemDetailsBinding
import com.nurbk.ps.countryweather.model.DetailsData
import com.nurbk.ps.countryweather.model.ObjectDetails
import javax.inject.Inject

class ParentDetailsAdapter @Inject constructor(

) : RecyclerView.Adapter<ParentDetailsAdapter.ParentDetailsViewHolder>() {

    var data: DetailsData = DetailsData("", arrayListOf())


    inner class ParentDetailsViewHolder(val item: ItemDetailsBinding) :
        RecyclerView.ViewHolder(item.root) {
        lateinit var itemAdapter: ItemParentDetailsAdapter
        fun bind(data: ObjectDetails) {
            item.item = data
            itemAdapter = ItemParentDetailsAdapter()
            item.rcData.apply {
                adapter = itemAdapter

                itemAdapter.data = data
                layoutManager = if (data.type == 4) {
                    StaggeredGridLayoutManager(1, GridLayoutManager.HORIZONTAL)
                } else {
                    StaggeredGridLayoutManager(4, GridLayoutManager.HORIZONTAL)
                }
                itemAnimator = DefaultItemAnimator()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentDetailsViewHolder {
        return ParentDetailsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_details, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ParentDetailsViewHolder, position: Int) {
        holder.bind(data = data.dataList[position])
    }

    override fun getItemCount() = data.dataList.size


}