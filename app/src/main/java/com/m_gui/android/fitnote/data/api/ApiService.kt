package com.m_gui.android.fitnote.data.api

import com.m_gui.android.fitnote.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
}