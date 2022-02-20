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
    private var _gameSaleInfoList = MutableLiveData(
        mutableListOf<GameSaleInfo>( //dummy data
//        GameSaleInfo("Fortnite 2: Fort Harder", 9.99),
//        GameSaleInfo("Among Us Battle Royale", 29.99),
//        GameSaleInfo("Haywire",420.69),
//        GameSaleInfo("Red Dead Rodyushkin", 79.99)
        )
    )
    val gameSaleInfoList: LiveData<MutableList<GameSaleInfo>>
        get() = _gameSaleInfoList

    fun getSavedGameList(): MutableList<GameSaleInfo>? {
        return _gameSaleInfoList.value
    }

    private var _onlineGameList = MutableLiveData(
        mutableListOf<GameSaleInfo>()
    )
    val onlineGameList: LiveData<MutableList<GameSaleInfo>>
        get() = _onlineGameList

    fun getOnlineGameList(): MutableList<GameSaleInfo>? {
        return _onlineGameList.value
    }

    private var _toastButFromViewModel = MutableLiveData("")
    val toastButFromViewModel: LiveData<String>
        get() = _toastButFromViewModel

    private var _updateRecyclerView = MutableLiveData(false)
    val updateRecyclerView: LiveData<Boolean>
        get() = _updateRecyclerView

    fun resetUpdateVariable() {
        _updateRecyclerView.value = false
    }

    fun addGame(game: GameSaleInfo) {
        if (_gameSaleInfoList.value?.contains(game) == false) {
            _gameSaleInfoList.value?.add(game)
            getPrice(game)
            _toastButFromViewModel.value = "Saved " + game.name + " to list!"
        } else {
            _toastButFromViewModel.value = game.name + " is already saved to list"
        }
    }

    fun addDummyGame() {
        _gameSaleInfoList.value?.add(GameSaleInfo("Celeste",4.99,
            "https://www.microsoft.com/en-us/p/celeste/bwmql2rpwbhb",
            "https://store-images.s-microsoft.com/image/apps.21257.71633162879241707.7cf18b3b-9fa5-486f-9a68-067f06d50bf1.8f7909cf-d9a5-44aa-9901-2635255ab2ee?w=162&h=300&q=90&mode=scale",true))
    }

    fun search(name: String) { //dummy search thing
//        _onlineGameList.value = mutableListOf(
//            GameSaleInfo(name,27.99),
//            GameSaleInfo(name + " 2",28.99),
//            GameSaleInfo(name + " 2 but again",29.99)
//        )
//        Log.i("help",_onlineGameList.value.toString())

        _toastButFromViewModel.value = "Searching for $name..."
        thread {
            Log.i("help","search link = https://www.microsoft.com/en-us/search/shop/games?q=$name&devicetype=xbox")
            name.replace(' ', '+')
            val resultsPage =
                Jsoup.connect("https://www.microsoft.com/en-us/search/shop/games?q=$name&devicetype=xbox")
                    .get()
            val gameTitleElements = resultsPage.getElementsByClass("c-subheading-6")
            val gameContainerElements = resultsPage.getElementsByClass("m-channel-placement-item")
            val gameImageElements = resultsPage.getElementsByClass("c-channel-placement-image")

            if (gameTitleElements.size > 0) {
                var resultsList = mutableListOf<GameSaleInfo>()
                var upperBound = 20 //for some reason it was being weird about me importing min
                if (20 > gameTitleElements.size)
                    upperBound = gameTitleElements.size - 1
                for (i in 0..upperBound) {
                    var newGame = GameSaleInfo( // /en-us/p/celeste/bwmql2rpwbhb https://www.xbox.com/en-us/games/store
                        gameTitleElements[i].text(),
//                        url = "https://microsoft.com" + gameContainerElements[i].children()[0].attr("href"), // version that went with old xbox store
                        url = "https://www.xbox.com/en-us/games/store" + gameContainerElements[i].children()[0].attr("href").substring(8),
                        imageUrl = gameImageElements[i].children()[0].children()[0].absUrl("data-srcset")
                    )
                    getPrice(newGame)
                    resultsList.add(newGame)
                }
                _onlineGameList.postValue(resultsList)
            } else {
                _toastButFromViewModel.postValue("English, please")
            }
        }
    }

    fun removeGame(game: GameSaleInfo) {
        _gameSaleInfoList.value?.remove(game)
    }

    fun assignGameList(games: MutableList<GameSaleInfo>) {
        _gameSaleInfoList.postValue(games)
    }

    fun getPrice(game: GameSaleInfo, updateRecyclerView: Boolean = false) {
        thread {
            Log.i("help",game.url)
            val gamePage = Jsoup.connect(game.url).get()
//            var priceText = gamePage.getElementById("productPrice")
//                .children()[0].children()[0].children()[0].children()[0].text()
            var thing1 = gamePage.getElementsByClass("ProductDetailsHeader-module__showOnMobileView___XQCPo ProductDetailsHeader-module__price___2Gqs8")
            Log.i("help","among us sus sussy sussy amogn ussysa suss amogn suss susyssusysyss "+thing1.toString())
            var priceText = "among us";
            if(thing1.size>0) {
                priceText = thing1[0].children()[0].text()
            }
            else{
                priceText = "among us"
            }
            Log.i("help","el susso gusso " + thing1.size)
            if (priceText != "Free") {
                try {
                    var sussyThing = priceText.substring(1 until priceText.length)
                    if(sussyThing[sussyThing.length-1]=='+'){
                        sussyThing = sussyThing.substring(0 until priceText.length-2)
                    }
                    var price = sussyThing.toDouble()
                    var saleStatus = false
                    try {
                        if(thing1[0].children().size==2){
                            priceText = thing1[0].children()[1].text()
                            sussyThing = priceText.substring(1 until priceText.length)
                            if(sussyThing[sussyThing.length-1]=='+'){
                                sussyThing = sussyThing.substring(0 until priceText.length-2)
                            }
                            price = sussyThing.toDouble()
                            saleStatus=true
                        }
                    } catch (e: Exception) {
                        saleStatus = false
                    }
                    game.price = price
                    game.isOnSale = saleStatus
                    if(updateRecyclerView)
                        _updateRecyclerView.postValue(true)
                }
                catch(e: Exception){ //in the case that the game is unreleased, or not sold separately
                    game.price = -1.0
                    game.isOnSale = false
                }
            } else {
                game.price = 0.0
                game.isOnSale = false
            }
        }
    }

    fun updatePrices(games: MutableList<GameSaleInfo>) {
        for (game in games) {
            getPrice(game,true)
        }
    }

    fun updateGameSaleInfoPrices() {
        updatePrices(_gameSaleInfoList.value!!)
    }
}
