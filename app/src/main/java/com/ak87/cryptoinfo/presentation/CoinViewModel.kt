package com.ak87.cryptoinfo.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ak87.cryptoinfo.data.network.models.CoinInfoDto
import com.ak87.cryptoinfo.data.repository.CoinRepositoryImpl
import com.ak87.cryptoinfo.domain.GetCoinInfoListUseCase
import com.ak87.cryptoinfo.domain.GetCoinInfoUseCase
import com.ak87.cryptoinfo.domain.LoadDataUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()


    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}