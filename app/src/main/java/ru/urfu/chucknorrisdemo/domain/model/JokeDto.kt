package ru.urfu.chucknorrisdemo.data.model

import com.google.gson.annotations.SerializedName

data class JokeDto(
    @SerializedName("value")
    val value: String
)
