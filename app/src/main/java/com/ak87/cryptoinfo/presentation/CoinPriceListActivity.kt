package com.ak87.cryptoinfo.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ak87.cryptoinfo.R
import com.ak87.cryptoinfo.presentation.adapters.CoinInfoAdapter
import com.ak87.cryptoinfo.databinding.ActivityCoinPriceListBinding
import com.ak87.cryptoinfo.data.network.models.CoinInfoDto


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPriceListBinding
    private lateinit var viewModel: CoinViewModel

    private var baskPressedTime: Long = 0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onClick(coinPriceInfo: CoinInfoDto) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
                //Toast.makeText(this@CoinPriceListActivity, coinPriceInfo.fromSymbol, Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvCoinPriceList.adapter = adapter


        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })

    }

    override fun onBackPressed() {
        if (baskPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            return
        } else {
            backToast =
                Toast.makeText(baseContext, R.string.exit_message, Toast.LENGTH_SHORT)
            backToast?.show()
        }
        baskPressedTime = System.currentTimeMillis()
    }

}