package com.souvik.cryptoapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.souvik.cryptoapp.R
import com.souvik.cryptoapp.adapter.TopMarketAdapter
import com.souvik.cryptoapp.api.ApiInterface
import com.souvik.cryptoapp.api.ApiUtilities
import com.souvik.cryptoapp.databinding.FragmentHomeBinding
import com.souvik.cryptoapp.models.MarketModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        getTopCurrencyList()

        return binding.root
    }

    private fun getTopCurrencyList() {

        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            withContext(Dispatchers.Main) {
                binding.topCurrencyRecyclerView.adapter = TopMarketAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
            }

            Log.d("SUI", "getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }
    }
}