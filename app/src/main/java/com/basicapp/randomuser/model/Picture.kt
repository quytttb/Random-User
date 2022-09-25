package com.basicapp.randomuser.model

import com.squareup.moshi.Json

data class Picture(
    @Json
    var large: String
)