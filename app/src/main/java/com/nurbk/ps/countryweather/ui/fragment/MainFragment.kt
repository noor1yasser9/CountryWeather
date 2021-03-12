package com.nurbk.ps.countryweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.adapters.CountriesAdapter
import com.nurbk.ps.countryweather.databinding.FragmentMainBinding
import com.nurbk.ps.countryweather.model.countries.CountriesPage
import com.nurbk.ps.countryweather.ui.viewmodel.CountriesViewModel
import com.nurbk.ps.countryweather.utils.ConstanceString
import com.nurbk.ps.countryweather.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding

    @Inject
    lateinit var  viewModel: CountriesViewModel

    @Inject
    lateinit var countriesAdapter: CountriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentMainBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        requireActivity().title = getString(R.string.countries)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllCountries()

        lifecycleScope.launchWhenStarted {
            viewModel.getSearchLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        Result.Status.LOADING -> {
                            showLoading()
                        }
                        Result.Status.SUCCESS -> {
                            dismissLoading()
                            val data = it.data as CountriesPage
                            countriesAdapter.countriesList = data
                            countriesAdapter.notifyDataSetChanged()
                        }
                        Result.Status.ERROR -> {
                            dismissLoading()
                        }
                    }
                }
            }
        }
        mBinding.rcData.apply {
            adapter = countriesAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            itemAnimator = DefaultItemAnimator()
        }


        var isSearching = false

        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.searchCountries(newText)
                    isSearching = true
                } else {
                    if (isSearching) {
                        isSearching = false
                        getAllCountries()
                    }
                }
                return false
            }
        })

        countriesAdapter.setItemClickListener { country ->
            val bundle = Bundle()
            bundle.putParcelable(ConstanceString.DATA_DETAILS, country)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            viewModel.getNameCountries(country.countryInfo.iso2, country.country)
        }


    }

    private fun getAllCountries() {

        lifecycleScope.launchWhenStarted {
            viewModel.getCountriesLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        Result.Status.LOADING -> {
                            showLoading()
                        }
                        Result.Status.SUCCESS -> {
                            dismissLoading()
                            val data = it.data as CountriesPage
                            countriesAdapter.countriesList = data
                            countriesAdapter.notifyDataSetChanged()
                        }
                        Result.Status.ERROR -> {
                            dismissLoading()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        mBinding.loading.isVisible=true
    }

    private fun dismissLoading() {
        mBinding.loading.isVisible=false
    }
}