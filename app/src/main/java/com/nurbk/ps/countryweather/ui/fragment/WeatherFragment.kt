package com.nurbk.ps.countryweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.adapters.WeatherAdapter
import com.nurbk.ps.countryweather.databinding.FragmentWeatherBinding
import com.nurbk.ps.countryweather.model.weather.currentweather.CurrentWeatherResponse
import com.nurbk.ps.countryweather.model.weather.fivedayweather.FiveDayResponse
import com.nurbk.ps.countryweather.model.weather.fivedayweather.ItemHourly
import com.nurbk.ps.countryweather.ui.viewmodel.WeatherViewModel
import com.nurbk.ps.countryweather.utils.AppUtil
import com.nurbk.ps.countryweather.utils.ConstanceString.DATA_DETAILS
import com.nurbk.ps.countryweather.utils.Result
import com.nurbk.ps.countryweather.utils.TextViewFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var mBinding: FragmentWeatherBinding

    private val viewModel: WeatherViewModel by viewModels()

    private val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter()
    }

    private val dialog by lazy {
        MultipleDaysFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentWeatherBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        weatherAdapter.type = 1
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindTextSwitcher()

        lifecycleScope.launchWhenStarted {
            viewModel.getWeatherLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {

                    }
                    Result.Status.SUCCESS -> {
                        try {
                            val data = it.data as CurrentWeatherResponse
                            bindData(data)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    Result.Status.ERROR -> {

                    }
                }
            }
        }
        var details = WeatherDetailsFragment()
        lifecycleScope.launchWhenStarted {
            viewModel.getFiveWeatherLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {

                    }
                    Result.Status.SUCCESS -> {
                        try {
                            val item = it.data as FiveDayResponse
                            weatherAdapter.data = item.list!!.subList(0, 3)
                            mBinding.animationView.setOnClickListener {
                                if (!details.isAdded) {
                                    details.arguments=Bundle().apply { putParcelable(DATA_DETAILS, item) }
                                    details.show(requireActivity().supportFragmentManager, "")
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    Result.Status.ERROR -> {

                    }
                }
            }
        }
        mBinding.recyclerView.apply {
            adapter = weatherAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3)

        }

        mBinding.nextDaysButton.setOnClickListener {
            if (!dialog.isAdded)
                dialog.show(requireActivity().supportFragmentManager, "")
        }

        dialog.onChange = object : MultipleDaysFragment.OnStateChangedDialog {
            override fun onChanged(setOff: Float) {

            }

        }
        weatherAdapter.setItemClickListener {
            if (it is ItemHourly) {

            } else {

            }
        }
    }

    private fun bindData(data: CurrentWeatherResponse) {
        mBinding.tempTextView.setText(
            String.format(
                Locale.getDefault(),
                "%.0f°",
                data.main!!.temp
            )
        )

        mBinding.descriptionTextView.setText(
            AppUtil.getWeatherStatus(
                data.id,
                AppUtil.isRTL(requireContext())
            )
        )
        mBinding.humidityTextView.setText(
            java.lang.String.format(
                Locale.getDefault(),
                "%d%%",
                data.main!!.humidity
            )
        )
        mBinding.windTextView.setText(
            java.lang.String.format(
                Locale.getDefault(),
                resources.getString(R.string.wind_unit_label),
                data.wind!!.speed
            )
        )
        mBinding.animationView.setAnimation(
            AppUtil.getWeatherAnimation(
                data.weather!![0].id, mBinding.statsTextView
            )
        )
        mBinding.animationView.playAnimation()
    }


    private fun bindTextSwitcher() {
        mBinding.tempTextView.setFactory(
            TextViewFactory(
                requireContext(),
                R.style.TempTextView,
                true
            )
        )
        mBinding.descriptionTextView.setFactory(
            TextViewFactory(
                requireContext(),
                R.style.DescriptionTextView,
                true,
            )
        )
        mBinding.statsTextView.setFactory(
            TextViewFactory(
                requireContext(),
                R.style.DescriptionTextView,
                true,
            )
        )

        mBinding.humidityTextView.setFactory(
            TextViewFactory(
                requireContext(),
                R.style.HumidityTextView,
                false
            )
        )
        mBinding.windTextView.setFactory(
            TextViewFactory(
                requireContext(),
                R.style.WindSpeedTextView,
                false
            )
        )
    }


}