package com.example.kursach_6th_sem.api.entities

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class TaskData (
    @JsonProperty("UUID")
    val uuid: String?,
    @JsonProperty("TaskName")
    val taskName: String?,
    @JsonProperty("Description")
    val description: String?,
    @JsonProperty("Status")
    val status: String?,
    @JsonProperty("Priority")
    val priority: Boolean?,
    @JsonProperty("Deadline")
    val deadline: Date?,
    @JsonProperty("ProjectUuid")
    val projectUuid: String?
){
}