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
import com.ak87.cryptoinfo.domain.CoinInfo
import com.ak87.cryptoinfo.presentation.adapters.CoinDetailFragment


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
            override fun onClick(coinPriceInfo: CoinInfo) {
                if (onPainMode()) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null


        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    private fun onPainMode() = binding.fragmentContainer == null

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

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
        //Toast.makeText(this@CoinPriceListActivity, coinPriceInfo.fromSymbol, Toast.LENGTH_SHORT).show()
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

}