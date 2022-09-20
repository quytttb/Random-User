package com.basicapp.randomuser.model

import com.squareup.moshi.Json


data class Location (
    @Json(name= "city")
    var city: String,

    @Json(name= "state")
    var state: String

)