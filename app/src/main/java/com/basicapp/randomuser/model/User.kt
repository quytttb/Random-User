package com.basicapp.randomuser.model

import com.squareup.moshi.Json

//@Json(name = "results")
class User(
    @Json
    var gender: String,

    @Json
    var name: Name,

    @Json
    var location: Location,

    @Json
    var email: String,

    @Json
    var picture: Picture
) {
}