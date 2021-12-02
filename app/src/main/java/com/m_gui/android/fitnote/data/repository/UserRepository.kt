package com.m_gui.android.fitnote.data.repository

import com.m_gui.android.fitnote.data.api.ApiHelper

class UserRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers() = apiHelper.getUser()
}