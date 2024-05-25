package com.example.kursach_6th_sem.api

import com.example.kursach_6th_sem.api.entities.LoginDto
import com.example.kursach_6th_sem.api.entities.MessageData
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.api.entities.TaskData
import com.example.kursach_6th_sem.api.entities.UserData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("projects/get-all")
    suspend fun getAllProjects(): List<ProjectData>


    @GET("projects/get/{userUuid}")
    suspend fun getProjectsByUserUuid(@Path("userUuid") userUuid: String): List<ProjectData>

    @POST("projects/create")
    suspend fun createProject(@Body projectData: ProjectData) : ProjectData

    @POST("projects/edit")
    suspend fun editProject(@Body projectData: ProjectData) : ProjectData

    @POST("tasks/create")
    suspend fun createTask(@Body taskData: TaskData) : TaskData

    @GET("projects/get-project-tasks/{projectUuid}")
    suspend fun getProjectTasks(@Path("projectUuid") projectUuid: String) : List<TaskData>


    @POST("login")
    suspend fun login(@Body loginDto: LoginDto) : UserData?

    @POST("register")
    suspend fun createUser(@Body userData: UserData)


    @GET("check-email/{email}")
    suspend fun checkEmail(@Path("email") email: String) : MessageData

    @DELETE("projects/delete/{projectUuid}")
    suspend fun deleteProject(@Path("projectUuid") projectUuid: String)

    @DELETE("projects/delete-task-from-project/{taskUuid}/{projectUuid}")
    suspend fun deleteTask(@Path("taskUuid") taskUuid: String, @Path("projectUuid") projectUuid: String)

    @POST("tasks/edit")
    suspend fun editTask(@Body taskData: TaskData) : TaskData

    @POST("tasks/change-priority/{taskUuid}")
    suspend fun changeTaskPriority(@Path("taskUuid") taskUuid: String)

    @POST("projects/change-priority/{projectUuid}")
    suspend fun changeProjectPriority(@Path("projectUuid") projectUuid: String)

}