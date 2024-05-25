package com.example.kursach_6th_sem.api.entities

import android.os.Parcel
import android.os.Parcelable
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
    val deadline: String?,
    @JsonProperty("ProjectUuid")
    val projectUuid: String?
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readValue(Boolean::class.java.classLoader) as Boolean?,
        parcel.readString().toString(),
        parcel.readString().toString()
    )


    companion object CREATOR : Parcelable.Creator<TaskData> {
        override fun createFromParcel(parcel: Parcel): TaskData {
            return TaskData(parcel)
        }

        override fun newArray(size: Int): Array<TaskData?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(uuid)
        dest.writeString(taskName)
        dest.writeString(description)
        dest.writeString(status)
        dest.writeValue(priority)
        dest.writeString(deadline)
        dest.writeString(projectUuid)
    }
}