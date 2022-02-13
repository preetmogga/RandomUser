package com.mogga.randomuser.mainviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mogga.randomuser.model.User
import com.mogga.randomuser.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository):ViewModel() {

    suspend fun getData(){
 repository.getUserData()
    }

    init {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserData()

        }
    }
    val user:LiveData<User>
    get() = repository.user

}