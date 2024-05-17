package com.example.kursach_6th_sem.api.entities

data class UserData(
    val uuid: String,
    val username: String,
    val password: String,
    val email: String,
    val projectList: MutableList<ProjectData>?
){}
