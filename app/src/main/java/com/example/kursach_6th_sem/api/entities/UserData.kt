package com.example.kursach_6th_sem.api.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class UserData(
    @JsonProperty("uuid")
    val uuid: String?,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("password")
    val password: String,
    @JsonProperty("email")
    val email: String
)
