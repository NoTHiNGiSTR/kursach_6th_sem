package com.example.kursach_6th_sem.api

import com.example.kursach_6th_sem.api.entities.ProjectData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("projects/get-all")
    suspend fun getAllProjects(): List<ProjectData>
}