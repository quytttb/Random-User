package com.basicapp.randomuser.model

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json

class User(
    @Json(name = "gender")
    var gender: String,

    @Json(name = "name")
    var name: Name,

    @Json(name = "location")
    var location: Location,

    @Json(name = "email")
    var email: String,

    @Json(name = "picture")
    var picture: Picture

) {
}