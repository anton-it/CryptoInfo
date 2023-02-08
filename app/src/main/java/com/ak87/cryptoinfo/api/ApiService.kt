package com.ak87.cryptoinfo.api

import com.ak87.cryptoinfo.pojo.CoinInfoListOfData
import com.ak87.cryptoinfo.pojo.CoinPriceInfoRowData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET ("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) toSym: String = CURRENCY
    ): Single<CoinInfoListOfData>

    @GET ("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOLS)  fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS)  toSyms: String = CURRENCY
    ): Single<CoinPriceInfoRowData>

    companion object {
        private const val QUERY_PARAM_APY_KEY = "apy_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"

        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY = "USD"
    }
}