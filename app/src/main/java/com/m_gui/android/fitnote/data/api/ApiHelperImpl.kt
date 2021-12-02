package com.m_gui.android.fitnote.data.api

import com.m_gui.android.fitnote.data.model.User

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUser(): List<User> {
        return apiService.getUsers()
    }
}