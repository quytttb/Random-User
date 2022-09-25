package com.basicapp.randomuser.model

import com.squareup.moshi.Json


data class Location (
    @Json
    var city: String,

    @Json
    var state: String

)