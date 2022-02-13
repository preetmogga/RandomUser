package com.mogga.randomuser.api

import com.mogga.randomuser.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserInterface {


    @GET(value = "api")
    suspend fun getUser():Response<User>
}