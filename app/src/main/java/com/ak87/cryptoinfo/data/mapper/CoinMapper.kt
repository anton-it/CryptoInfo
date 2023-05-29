package com.ak87.cryptoinfo.data.mapper

import com.ak87.cryptoinfo.data.database.CoinInfoDbModel
import com.ak87.cryptoinfo.data.network.models.CoinInfoDto
import com.ak87.cryptoinfo.data.network.models.CoinInfoJsonContainerDto
import com.ak87.cryptoinfo.data.network.models.CoinNamesListDto
import com.ak87.cryptoinfo.domain.CoinInfo
import com.google.gson.Gson

class CoinMapper {

    fun mupDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel {
        return CoinInfoDbModel(
            fromSymbol = dto.fromSymbol,
            toSymbol = dto.toSymbol,
            price = dto.price,
            lastUpdate = dto.lastUpdate,
            highDay = dto.highDay,
            lowDay = dto.lowDay,
            lastMarket = dto.lastMarket,
            imageUrl = dto.imageUrl
        )
    }

    fun mupJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        if (jsonObject == null) return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result

    }

    fun mupNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lastUpdate = dbModel.lastUpdate,
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }
}