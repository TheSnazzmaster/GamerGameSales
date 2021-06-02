package com.honorsmobileapps.mathers.gamergamesales.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfo

class MainViewModel : ViewModel() {
//    private val gameSaleInfoList: MutableList<GameSaleInfo> = mutableListOf(
//        GameSaleInfo("Fortnite 2: Fort Harder", 9.99),
//        GameSaleInfo("Among Us Battle Royale", 29.99),
//        GameSaleInfo("Haywire",420.69),
//        GameSaleInfo("Red Dead Rodyushkin", 79.99)
//    )
    private val _gameSaleInfoList = MutableLiveData(mutableListOf(
        GameSaleInfo("Fortnite 2: Fort Harder", 9.99),
        GameSaleInfo("Among Us Battle Royale", 29.99),
        GameSaleInfo("Haywire",420.69),
        GameSaleInfo("Red Dead Rodyushkin", 79.99)
    ))
    val gameSaleInfoList: LiveData<MutableList<GameSaleInfo>>
        get()=_gameSaleInfoList
    fun getGameList(): MutableList<GameSaleInfo>? {
        return _gameSaleInfoList.value
    }
    fun addGame(game: GameSaleInfo){
        _gameSaleInfoList.value?.add(game)
    }
}
