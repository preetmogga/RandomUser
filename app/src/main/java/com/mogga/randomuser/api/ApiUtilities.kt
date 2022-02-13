package com.mogga.randomuser.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {

    private const val baseUrl = "https://randomuser.me/"

fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
           .addConverterFactory(GsonConverterFactory.create())
            .build()
}
}