package com.basicapp.randomuser.model

import com.squareup.moshi.Json

data class Name(
    @Json(name= "first")
    var first: String,

    @Json(name= "last")
    var last: String

)