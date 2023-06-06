package com.ak87.cryptoinfo.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ak87.cryptoinfo.R
import com.ak87.cryptoinfo.databinding.ItemCoinInfoBinding
import com.ak87.cryptoinfo.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfo> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinInfoBinding.inflate(inflater, parent, false)

        return CoinInfoViewHolder(binding)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList.get(position)

        with(holder.binding) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
                tvPrice.text = coin.price
                tvLastUpdate.text = String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(ivLogoCoin)

                root.setOnClickListener {
                    onCoinClickListener?.onClick(coin)
            }
            }
        }
    }


    interface OnCoinClickListener {
        fun onClick(coinPriceInfo: CoinInfo)
    }
}