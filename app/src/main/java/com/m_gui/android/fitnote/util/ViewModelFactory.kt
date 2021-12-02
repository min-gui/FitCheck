package com.m_gui.android.fitnote.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.m_gui.android.fitnote.data.api.ApiHelper
import com.m_gui.android.fitnote.data.repository.UserRepository
import com.m_gui.android.fitnote.ui.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(UserRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}