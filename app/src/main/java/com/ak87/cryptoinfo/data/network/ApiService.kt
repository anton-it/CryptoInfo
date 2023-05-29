package com.ak87.cryptoinfo.data.network

import com.ak87.cryptoinfo.data.network.models.CoinNamesListDto
import com.ak87.cryptoinfo.data.network.models.CoinInfoJsonContainerDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET ("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) toSym: String = CURRENCY
    ): CoinNamesListDto

    @GET ("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOLS)  fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS)  toSyms: String = CURRENCY
    ): CoinInfoJsonContainerDto

    companion object {
        private const val QUERY_PARAM_APY_KEY = "apy_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"

        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY = "USD"
    }
}