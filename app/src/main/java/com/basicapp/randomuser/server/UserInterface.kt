package com.basicapp.randomuser.server

import com.basicapp.randomuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInterface {

    @GET("/api/")
    fun getUsers(@Query("results") results: Int): Call<UserResponse>
}