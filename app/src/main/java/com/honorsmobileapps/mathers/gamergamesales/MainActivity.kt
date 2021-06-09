package com.honorsmobileapps.mathers.gamergamesales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainFragment
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        Log.i("help","super load")
        viewModel.assignGameList(getGamesFromPreferences())
        Toast.makeText(this,"Updating price info...",Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        viewModel.getSavedGameList()?.let { saveGamesToPreferences(it) }
    }

    //this saving stuff was learned from https://www.youtube.com/watch?v=8zPkbV4INGA&ab_channel=ResoCoderResoCoder
    //well i say "learned" but i really mean "yoinked", but i think i understand it well enough to use it
    fun saveGamesToPreferences(games: List<GameSaleInfo>){
        val prefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        val jsonString = Gson().toJson(games)
        prefEditor.putString("games",jsonString).apply()
    }
    fun getGamesFromPreferences(): MutableList<GameSaleInfo>{
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val jsonString = preferences.getString("games",null)

        return if (jsonString!=null)
            Gson().fromJson(jsonString,object: TypeToken<MutableList<GameSaleInfo>>(){}.type)
        else
            mutableListOf()
    }

}
