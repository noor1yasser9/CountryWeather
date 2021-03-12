package com.nurbk.ps.countryweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ItemSliderImageBinding
import com.nurbk.ps.countryweather.model.photos.Photo

class ItemPagerSlider(val glide: RequestManager, var listData: List<Photo>) :
    RecyclerView.Adapter<ItemPagerSlider.PagerViewHolder>() {

    inner class PagerViewHolder(val mBinding: ItemSliderImageBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(photo: Photo) {
            glide.load(getUrl(photo)).into(mBinding.imageView)

            mBinding.floatingActionButton.setOnClickListener {
                onItemClickListener?.let {
                    it(photo)
                }
            }
        }

        private fun getUrl(photo: Photo): String {
            return "https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_w.jpg"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_slider_image,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        try {
            holder.bind(listData[position])
        } catch (e: Exception) {

        }
    }

    override fun getItemCount() = listData.size

    private var onItemClickListener: ((Photo) -> Unit)? = null

    fun setItemClickListener(listener: (Photo) -> Unit) {
        onItemClickListener = listener
    }

}