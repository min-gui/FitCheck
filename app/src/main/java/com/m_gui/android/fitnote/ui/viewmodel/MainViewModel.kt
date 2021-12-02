package com.m_gui.android.fitnote.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m_gui.android.fitnote.data.repository.UserRepository
import com.m_gui.android.fitnote.ui.intent.MainIntent
import com.m_gui.android.fitnote.ui.viewstate.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel (
    private val repository: UserRepository
        ) : ViewModel(){

    val TAG = javaClass.simpleName
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state : StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent(){
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it){
                    is MainIntent.FetchUser -> fetchUser()
                }
            }
        }
    }

    private fun fetchUser(){
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.User(repository.getUsers())

            }catch (e : Exception){
                MainState.Error(e.localizedMessage)
            }

        }
    }

}