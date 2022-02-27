package com.honorsmobileapps.mathers.gamergamesales

data class GameSaleInfo(val name: String, var price: Double = 0.0, val url: String = "", val imageUrl: String="", var isOnSale: Boolean = false){
    override fun toString(): String {
        return "name: $name price: \$$price sale?: $isOnSale url: $url image url: $imageUrl"
    }

}