package com.mogga.randomuser.mainviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mogga.randomuser.repository.Repository

class MainViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}