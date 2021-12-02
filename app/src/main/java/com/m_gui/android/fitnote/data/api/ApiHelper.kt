package com.m_gui.android.fitnote.data.api

import com.m_gui.android.fitnote.data.model.User

interface ApiHelper {
    suspend fun getUser(): List<User>
}