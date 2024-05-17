package com.example.kursach_6th_sem.api.entities

import androidx.room.ColumnInfo
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class ProjectData(
    @JsonProperty("UUID")
    var uuid: String?,
    @JsonProperty("ProjectName")
    var projectName: String?,
    @JsonProperty("Description")
    var description: String?,
    @JsonProperty("Date")
    var deadline: Date?,
    @JsonProperty("Status")
    var status: String?,
    @JsonProperty("Priority")
    var priority: Boolean?,
    @JsonProperty("TaskList")
    var taskList: MutableList<TaskData>?,
    @JsonProperty("UserUuid")
    var userUuid: String?
)
