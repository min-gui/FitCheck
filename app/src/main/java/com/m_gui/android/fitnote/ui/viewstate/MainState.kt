package com.m_gui.android.fitnote.ui.viewstate

import com.m_gui.android.fitnote.data.model.User


sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    data class User(val user: List<com.m_gui.android.fitnote.data.model.User>) : MainState()
    data class Error(val error: String?) : MainState()
}