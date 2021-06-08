package com.honorsmobileapps.mathers.gamergamesales.ui.main

import android.app.PendingIntent.getActivity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfo
import java.security.AccessController.getContext

class MainViewModel : ViewModel() {
    private var _gameSaleInfoList = MutableLiveData(mutableListOf( //dummy data
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

    private var _onlineGameList = MutableLiveData(mutableListOf( //dummy data
        GameSaleInfo("fjortnite",27.99, "https://www.microsoft.com/en-us/p/grand-theft-gangsters/9nq5hr909vqh?activetab=pivot:overviewtab"),
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
    fun search(name: String){
        _onlineGameList.value = mutableListOf(
            GameSaleInfo(name,27.99),
            GameSaleInfo(name + " 2",28.99),
            GameSaleInfo(name + " 2 but again",29.99)
        )
        Log.i("help",_onlineGameList.value.toString())
    }

}
