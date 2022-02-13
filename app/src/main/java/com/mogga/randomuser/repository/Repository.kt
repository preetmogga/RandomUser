package com.mogga.randomuser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mogga.randomuser.api.UserInterface
import com.mogga.randomuser.model.User

class Repository(private val userInterface :UserInterface) {


    private val userLiveData = MutableLiveData<User>()

    val user :LiveData<User>
        get() = userLiveData

    suspend fun getUserData(){
        val result = userInterface.getUser()
        if (result.body() != null){
            userLiveData.postValue(result.body())

        }
    }


}