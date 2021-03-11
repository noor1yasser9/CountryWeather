package com.nurbk.ps.countryweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nurbk.ps.countryweather.adapters.ItemParentDetailsAdapter
import com.nurbk.ps.countryweather.databinding.FragmentSeeAllBinding
import com.nurbk.ps.countryweather.model.ObjectDetails
import com.nurbk.ps.countryweather.ui.viewmodel.CitiesViewModel
import com.nurbk.ps.countryweather.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SeeAllFragment : Fragment() {

    private lateinit var mBinding: FragmentSeeAllBinding
    private val viewModel: CitiesViewModel by viewModels()
    private val itemAdapter = ItemParentDetailsAdapter(ObjectDetails("", "", arrayListOf(), -1))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentSeeAllBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getAllCities()

        mBinding.rcData.apply {
            adapter = itemAdapter
            layoutManager = StaggeredGridLayoutManager(4, GridLayoutManager.VERTICAL)
        }
        var isSearching = false

        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.searchCities(newText)
                    isSearching = true
                } else {
                    if (isSearching) {
                        isSearching = false
//                        viewModel.searchCities()
                        getAllCities()
                    }
                }
                return false
            }
        })
        lifecycleScope.launchWhenStarted {
            viewModel.getCitiesSearchLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {
                    }
                    Result.Status.SUCCESS -> {
                        val data = it.data as ObjectDetails
                        itemAdapter.data = data
                        itemAdapter.notifyDataSetChanged()
                    }
                    Result.Status.ERROR -> {
                    }
                }
            }
        }
    }

    private fun getAllCities(){
        lifecycleScope.launchWhenStarted {
            viewModel.getCitiesLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {
                    }
                    Result.Status.SUCCESS -> {
                        val data = it.data as ObjectDetails
                        itemAdapter.data = data
                        itemAdapter.notifyDataSetChanged()
                    }
                    Result.Status.ERROR -> {
                    }
                }
            }

        }
    }

}