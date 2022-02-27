//package com.honorsmobileapps.mathers.gamergamesales.ui.main
//
//import android.app.Notification
//import android.app.NotificationManager
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import androidx.core.app.NotificationCompat
//import com.honorsmobileapps.mathers.gamergamesales.R
//
//const val notificationID = 1
//const val channelID = "channel1"
//const val titleExtra = "titleExtra"
//const val messageExtra = "messageExtra"
//
//class NotificationClass : BroadcastReceiver(){
//    override fun onReceive(context: Context, intent: Intent) {
//        val notification= NotificationCompat.Builder(context, channelID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(intent.getStringExtra(titleExtra))
//            .setContentText(intent.getStringExtra(messageExtra))
//            .build()
//
//        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(notificationID,notification)
//    }
//}