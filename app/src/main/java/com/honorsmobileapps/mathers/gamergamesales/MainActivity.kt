package com.honorsmobileapps.mathers.gamergamesales

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.observe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainFragment
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {
    //i put shared preferences  in the main activity
    //because it needs to happen before anything else in the program
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

//        Log.i("help", "super load")
//        viewModel.assignGameList(getGamesFromPreferences())
//        Toast.makeText(this, "Updating price info...", Toast.LENGTH_SHORT).show()
    }

//    override fun onStop() {
//        super.onStop()
//        viewModel.getSavedGameList()?.let { saveGamesToPreferences(it) }
//    }

    //this saving stuff was learned from https://www.youtube.com/watch?v=8zPkbV4INGA&ab_channel=ResoCoderResoCoder
    //well i say "learned" but the code is pretty much the same because I'm not sure how much can be changed,
    // but i think i understand it well enough to use it
    fun saveGamesToPreferences(games: List<GameSaleInfo>) {
        val prefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        val jsonString = Gson().toJson(games)
        prefEditor.putString("games", jsonString).apply()
    }

    fun getGamesFromPreferences(): MutableList<GameSaleInfo> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val jsonString = preferences.getString("games", null)

        return if (jsonString != null)
            Gson().fromJson(jsonString, object : TypeToken<MutableList<GameSaleInfo>>() {}.type)
        else
            mutableListOf()
    }
}
