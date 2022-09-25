package com.basicapp.randomuser.model

import com.squareup.moshi.Json

data class Name(
    @Json
    var first: String,

    @Json
    var last: String

)