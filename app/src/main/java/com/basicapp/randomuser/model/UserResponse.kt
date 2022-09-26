package com.basicapp.randomuser.model

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "results")
    var userList: List<User>
)