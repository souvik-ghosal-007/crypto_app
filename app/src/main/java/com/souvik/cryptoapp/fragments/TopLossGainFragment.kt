package com.souvik.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.souvik.cryptoapp.adapter.MarketAdapter
import com.souvik.cryptoapp.api.ApiInterface
import com.souvik.cryptoapp.api.ApiUtilities
import com.souvik.cryptoapp.databinding.FragmentTopLossGainBinding
import com.souvik.cryptoapp.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

class TopLossGainFragment: Fragment() {

    private lateinit var binding: FragmentTopLossGainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentTopLossGainBinding.inflate(layoutInflater)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {
        val position = requireArguments().getInt("position")

        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if(res.body() != null) {
                withContext(Dispatchers.Main) {
                    val dataItem = res.body()!!.data.cryptoCurrencyList

                    Collections.sort(dataItem) {
                            o1, o2 -> (o2.quotes[0].percentChange24h.toInt()).compareTo(o1.quotes[0].percentChange24h.toInt())
                    }

                    binding.spinKitView.visibility = View.GONE
                    val gainerList = ArrayList<CryptoCurrency>()
                    val loserList = ArrayList<CryptoCurrency>()

                    if(position == 0) {
                        gainerList.clear()
                        for(i in 0 until dataItem.size-1) {
                            gainerList.add(dataItem[i])
                        }

                        binding.topGainLoseRecyclerView.adapter = MarketAdapter(requireContext(), gainerList)
                    }
                    else {
                        loserList.clear()
                        for(i in 0 until dataItem.size-1) {
                            loserList.add(dataItem[dataItem.size - 1 - i])
                        }

                        binding.topGainLoseRecyclerView.adapter = MarketAdapter(requireContext(), loserList)
                    }
                }
            }
        }
    }
}