package com.honorsmobileapps.mathers.gamergamesales.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.util.ObjectsCompat.hash
import androidx.work.Worker
import androidx.work.ListenableWorker.Result
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfo
import com.honorsmobileapps.mathers.gamergamesales.MainActivity
import com.honorsmobileapps.mathers.gamergamesales.R
import org.jsoup.Jsoup
import java.lang.Exception
import kotlin.concurrent.thread


class BackgroundPriceUpdater(context: Context,params: WorkerParameters) : Worker(context,params) {
    val CHANNEL_ID = "among us sus channel id"

    override fun doWork(): Result {
        var retry = false
//        try {
            val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val jsonString = preferences.getString("games", null)

            var games: MutableList<GameSaleInfo>
            if (jsonString != null)
                games = Gson().fromJson(
                    jsonString,
                    object : TypeToken<MutableList<GameSaleInfo>>() {}.type
                )
            else
                games = mutableListOf()

            for(game in games){
                Log.i("help", game.toString())
            }

            var newGames = mutableListOf<GameSaleInfo>()
            for(game in games){
                Log.i("help", "BEFORE $game")
                var newGame = checkPrice(game,true,(1 until Int.MAX_VALUE).random())
                newGames.add(newGame)
                Log.i("help", "AFTER $game")
            }
            Log.i("help", "what the fucking heck")

            Log.i("help", "before supposed to write")
            val prefEditor =
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            val jsonString2 = Gson().toJson(newGames)
            prefEditor.putString("games", jsonString2).apply()
            Log.i("help", "after supposed to write")
            for (game in newGames) {
                Log.i("help", "" + game.name + " " + game.price)
            }


//                notificationStuff()


            return Result.success()
//        } catch (e: Exception) {
//            if(retry)
//                return Result.retry()
//            else
//                return Result.failure()
//        }
    }

    fun checkPrice(paramGame: GameSaleInfo, updateRecyclerView: Boolean = false, id:Int) : GameSaleInfo{
        var game = GameSaleInfo(paramGame.name,paramGame.price,paramGame.url,paramGame.imageUrl,paramGame.isOnSale)
        var retry = true
        var price: Double = 69.69
        var saleStatus: Boolean = false
//        thread {
//            try {
                Log.i("help", "checking price of " + game.name)
                val gamePage = Jsoup.connect(game.url).get()
                var thing1 =
                    gamePage.getElementsByClass("ProductDetailsHeader-module__showOnMobileView___XQCPo ProductDetailsHeader-module__price___2Gqs8")
                var priceText = "among us";
                if (thing1.size > 0) {
                    priceText = thing1[0].children()[0].text()
                } else {
                    priceText = "among us"
                }
                if (priceText != "Free") {
                    try {
                        var sussyThing = priceText.substring(1 until priceText.length)
                        if (sussyThing[sussyThing.length - 1] == '+') {
                            sussyThing = sussyThing.substring(0 until priceText.length - 2)
                        }
                        price = sussyThing.toDouble()
                        saleStatus = false
                        try {
                            if (thing1[0].children().size == 2) {
                                priceText = thing1[0].children()[1].text()
                                sussyThing = priceText.substring(1 until priceText.length)
                                if (sussyThing[sussyThing.length - 1] == '+') {
                                    sussyThing = sussyThing.substring(0 until priceText.length - 2)
                                }
                                price = sussyThing.toDouble()
                                saleStatus = true
                                if (saleStatus && !game.isOnSale) {
                                    Log.i(
                                        "help",
                                        "holy cr*p, " + game.name + " is on sale for $" + price + "!"
                                    )

                                    createNotificationChannel()
                                    var builder = createNotification(
                                        title = game.name + " is now on sale!",
                                        content = game.name + " - $" + price + " id: $id"
                                    )
                                    with(NotificationManagerCompat.from(applicationContext)) {
                                        notify(id, builder.build())
                                    }
                                    retry = false
                                }
                                Log.i("help","PRICE AND SALE STATUS: $price $saleStatus")
                                game.price = price
                                game.isOnSale = saleStatus
                                Log.i("help","PRICE AND SALE STATUS OF OBJECT: " + game.price + " " + game.isOnSale)
                            }
                        } catch (e: Exception) {
                            saleStatus = false
                        }
                        game.price = price
                        game.isOnSale = saleStatus
                    } catch (e: Exception) { //in the case that the game is unreleased, or not sold separately
                        game.price = -1.0
                        game.isOnSale = false
                    }
                } else {
                    game.price = 0.0
                    game.isOnSale = false
                }
//            }
//            catch (e: Exception){
//                game.price = -1.0
//                game.isOnSale = false
//            }
            Log.i("help","PRICE AND SALE STATUS: $price $saleStatus")
            game.price = price
            game.isOnSale = saleStatus
            Log.i("help","PRICE AND SALE STATUS OF OBJECT: " + game.price + " " + game.isOnSale)
//        }
        Log.i("help","IN checkPrice(): $game")
        return game
    }

    fun createNotification(content: String = "when the imposter is sus", title: String = "among us", longText : String = "i\nlove\namong us!"): NotificationCompat.Builder { // https://developer.android.com/training/notify-user/build-notification
//        val intent = Intent(this, AlertDetails::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        var builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.otherthing)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(longText)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        return builder
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Xbox Gamer Game Sales Notification Channel"
            val descriptionText = "get thine sales updated"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun notificationStuff() {
//        createNotificationChannel()
//        var builder = createNotification(content="mogus drippin")
//        with(NotificationManagerCompat.from(applicationContext)) {
//            notify(69, builder.build())
//        }
    }

//    fun notificationStuff(){
//        createNotificationChannel()
//        sendNotification()
//    }
//
//    fun sendNotification(){
//        val intent = Intent(applicationContext, NotificationClass::class.java)
//        val title = "among"
//        val message = "us"
//        intent.putExtra(titleExtra, title)
//        intent.putExtra(messageExtra, message)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            applicationContext,
//            notificationID,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        with(NotificationManagerCompat.from(applicationContext)) {
//            notify(notificationId, builder.build())
//        }
//    }
//
//    fun createNotificationChannel(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Notif Channel"
//            val desc = "notification channel description"
//            val importance = NotificationManager.IMPORTANCE_HIGH
//            val channel = NotificationChannel(channelID, name, importance)
//            channel.description = desc
//            val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}