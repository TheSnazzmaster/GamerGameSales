package com.honorsmobileapps.mathers.gamergamesales

data class GameSaleInfo(val name: String, var price: Double = 0.0, val url: String = "", val imageUrl: String="", var isOnSale: Boolean = false)