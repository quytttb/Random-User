package com.basicapp.randomuser.server

import com.basicapp.randomuser.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/api/")
    suspend fun getUsers(@Query("results") results: Int): Response<UserResponse>
}
