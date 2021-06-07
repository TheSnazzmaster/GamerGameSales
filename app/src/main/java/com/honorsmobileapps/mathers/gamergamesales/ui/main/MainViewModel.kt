package com.honorsmobileapps.mathers.gamergamesales.ui.main

import android.app.PendingIntent.getActivity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfo
import java.security.AccessController.getContext

class MainViewModel : ViewModel() {
//    private val gameSaleInfoList: MutableList<GameSaleInfo> = mutableListOf(
//        GameSaleInfo("Fortnite 2: Fort Harder", 9.99),
//        GameSaleInfo("Among Us Battle Royale", 29.99),
//        GameSaleInfo("Haywire",420.69),
//        GameSaleInfo("Red Dead Rodyushkin", 79.99)
//    )
    private var _gameSaleInfoList = MutableLiveData(mutableListOf(
        GameSaleInfo("Fortnite 2: Fort Harder", 9.99),
        GameSaleInfo("Among Us Battle Royale", 29.99),
        GameSaleInfo("Haywire",420.69),
        GameSaleInfo("Red Dead Rodyushkin", 79.99)
    ))
    val gameSaleInfoList: LiveData<MutableList<GameSaleInfo>>
        get()=_gameSaleInfoList
    fun getSavedGameList(): MutableList<GameSaleInfo>? {
        return _gameSaleInfoList.value
    }

    private var _onlineGameList = MutableLiveData(mutableListOf(
        GameSaleInfo("fjortnite",27.99),
        GameSaleInfo("fjortnite 2",28.99),
        GameSaleInfo("fjortnite 2 but again",29.99)
    ))
    val onlineGameList: LiveData<MutableList<GameSaleInfo>>
        get()=_onlineGameList
    fun getOnlineGameList(): MutableList<GameSaleInfo>?{
        return _onlineGameList.value
    }

    fun addGame(game: GameSaleInfo){
        _gameSaleInfoList.value?.add(game)
    }

}
