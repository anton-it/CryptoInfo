package com.ak87.cryptoinfo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.ak87.cryptoinfo.api.ApiFactory
import com.ak87.cryptoinfo.database.AppDatabase
import com.ak87.cryptoinfo.pojo.CoinPriceInfo
import com.ak87.cryptoinfo.pojo.CoinPriceInfoRowData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it.toString()) }
            .subscribeOn(Schedulers.io())
            .map { getPriceListFromRowData(it) }
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success: $it")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message.toString()}")

            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRowData(
        coinPriceInfoRowData: CoinPriceInfoRowData
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRowData.coinPriceInfoJsonObject
        if (jsonObject == null) return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}