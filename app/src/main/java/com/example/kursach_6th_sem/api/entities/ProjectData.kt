package com.example.kursach_6th_sem.api.entities

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName


data class ProjectData(
    @JsonProperty("UUID")
    var uuid: String?,
    @SerializedName("projectName")
    //@SerializedName("ProjectName")
    var projectName: String?,
    @JsonProperty("Description")
    var description: String?,
    @JsonProperty("Status")
    var status: String?,
    @JsonProperty("Priority")
    var priority: Boolean?,
    @JsonProperty("TaskList")
    var taskList: List<TaskData>?,
    @JsonProperty("UserUuid")
    var userUuid: String?
) : Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as Boolean?,
        parcel.createTypedArrayList(TaskData.CREATOR),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(projectName)
        parcel.writeString(description)
        parcel.writeString(status)
        parcel.writeValue(priority)
        parcel.writeTypedList(taskList)
        parcel.writeString(userUuid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectData> {
        override fun createFromParcel(parcel: Parcel): ProjectData {
            return ProjectData(parcel)
        }

        override fun newArray(size: Int): Array<ProjectData?> {
            return arrayOfNulls(size)
        }
    }
}
