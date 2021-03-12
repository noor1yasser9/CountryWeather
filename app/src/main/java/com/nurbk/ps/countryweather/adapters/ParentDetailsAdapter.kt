package com.nurbk.ps.countryweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ItemDetailsBinding
import com.nurbk.ps.countryweather.model.DetailsData
import com.nurbk.ps.countryweather.model.ObjectDetails
import com.nurbk.ps.countryweather.model.cities.City

class ParentDetailsAdapter constructor(

) : RecyclerView.Adapter<ParentDetailsAdapter.ParentDetailsViewHolder>() {

    var data: DetailsData = DetailsData("", arrayListOf())

    var onClick: OnClickListener? = null

    inner class ParentDetailsViewHolder(val item: ItemDetailsBinding) :
        RecyclerView.ViewHolder(item.root) {

        lateinit var itemAdapter: ItemParentDetailsAdapter

        fun bind(data: ObjectDetails) {
            item.item = data
            itemAdapter = ItemParentDetailsAdapter(data)
            item.rcData.apply {
                adapter = itemAdapter
                layoutManager = if (data.type == 4) {
                    StaggeredGridLayoutManager(1, GridLayoutManager.HORIZONTAL)
                } else {
                    StaggeredGridLayoutManager(4, GridLayoutManager.HORIZONTAL)
                }
                itemAnimator = DefaultItemAnimator()
            }
            itemAdapter.setItemClickListener { itemData ->
                onClick?.let {
                    it.onClickItemListener(itemData)
                }
            }
            if (data.data.size > 12 && data.data[0] is City) {
                item.btnSeeAll.isVisible = true
            }
            item.btnSeeAll.setOnClickListener {
                onClick?.let {
                    it.onClickSeeAllListener(3)
                }
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

    interface OnClickListener {
        fun onClickItemListener(data: Any)
        fun onClickSeeAllListener(i: Int)
    }

}