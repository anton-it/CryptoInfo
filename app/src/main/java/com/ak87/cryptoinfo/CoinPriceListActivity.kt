package com.ak87.cryptoinfo

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ak87.cryptoinfo.adapters.CoinInfoAdapter
import com.ak87.cryptoinfo.databinding.ActivityCoinPriceListBinding
import com.ak87.cryptoinfo.databinding.ItemCoinInfoBinding
import com.ak87.cryptoinfo.pojo.CoinPriceInfo


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPriceListBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onClick(coinPriceInfo: CoinPriceInfo) {
                Toast.makeText(this@CoinPriceListActivity, coinPriceInfo.fromSymbol, Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvCoinPriceList.adapter = adapter


        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })

    }
}