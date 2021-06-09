package com.honorsmobileapps.mathers.gamergamesales.ui.main

import android.app.PendingIntent.getActivity
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfo
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import java.security.AccessController.getContext
import kotlin.concurrent.thread

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

    private var _toastButFromViewModel = MutableLiveData("")
    val toastButFromViewModel: LiveData<String>
        get()=_toastButFromViewModel

    fun addGame(game: GameSaleInfo){
        if(_gameSaleInfoList.value?.contains(game) == false) {
            _gameSaleInfoList.value?.add(game)
            _toastButFromViewModel.value = "Saved " + game.name + " to list!"
        }
        else{
            _toastButFromViewModel.value = game.name + " is already saved to list"
        }
    }
    fun search(name: String){ //dummy search thing
//        _onlineGameList.value = mutableListOf(
//            GameSaleInfo(name,27.99),
//            GameSaleInfo(name + " 2",28.99),
//            GameSaleInfo(name + " 2 but again",29.99)
//        )
//        Log.i("help",_onlineGameList.value.toString())

        _toastButFromViewModel.value="Searching for $name..."
        thread{
            name.replace(' ','+')
            val resultsPage = Jsoup.connect("https://www.microsoft.com/en-us/search/shop/games?q=$name&devicetype=xbox").get()
            val gameTitleElements = resultsPage.getElementsByClass("c-subheading-6")
            val gameContainerElements = resultsPage.getElementsByClass("m-channel-placement-item")
            val gameImageElements = resultsPage.getElementsByClass("c-channel-placement-image")

            if(gameTitleElements.size>0) {
                var resultsList = mutableListOf<GameSaleInfo>()
                var upperBound = 20 //for some reason it was being weird about me importing min
                if (20 > gameTitleElements.size)
                    upperBound = gameTitleElements.size-1
                for (i in 0..upperBound) {
                    resultsList.add(
                        GameSaleInfo(
                            gameTitleElements[i].text(),
                            url = "https://microsoft.com" + gameContainerElements[i].children()[0].attr(
                                "href"
                            ),
                            imageUrl = gameImageElements[i].children()[0].children()[0].absUrl("data-srcset")
                        )
                    )
                }
                _onlineGameList.postValue(resultsList)
            }
            else{
                _toastButFromViewModel.postValue("English, please")
            }
        }
    }
    fun removeGame(game: GameSaleInfo){
        _gameSaleInfoList.value?.remove(game)
    }
    fun assignGameList(games: MutableList<GameSaleInfo>){
        _gameSaleInfoList.postValue(games)
    }
}
