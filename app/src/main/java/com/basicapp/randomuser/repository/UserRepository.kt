package com.basicapp.randomuser.repository

import com.basicapp.randomuser.server.UserService

class UserRepository(private val userService: UserService) {
    suspend fun getUsers(results: Int) = userService.getUsers(results)
}
