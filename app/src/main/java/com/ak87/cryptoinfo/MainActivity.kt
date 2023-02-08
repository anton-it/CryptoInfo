package com.ak87.cryptoinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ak87.cryptoinfo.api.ApiFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = ApiFactory.apiService.getFullPriceList(fSyms = "BTC,ETH,EOS")
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("TEST_OF_LOADING_DATA", it.toString())
            }, {
                Log.d("TEST_OF_LOADING_DATA", it.message.toString())

            })
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}