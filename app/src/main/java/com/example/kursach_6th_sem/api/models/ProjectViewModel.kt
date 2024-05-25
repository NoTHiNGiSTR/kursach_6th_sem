package com.example.kursach_6th_sem.api.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kursach_6th_sem.App
import com.example.kursach_6th_sem.adapters.ProjectListAdapter
import com.example.kursach_6th_sem.api.Api
import com.example.kursach_6th_sem.api.entities.ProjectData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class ProjectViewModel  : ViewModel() {
    val projectList = MutableLiveData<List<ProjectData>>()

    val projectApi = App.retrofit.create(Api::class.java)

    fun getAllProject(userUuid: String){
        CoroutineScope(Dispatchers.IO).launch {
            val temp = projectApi.getProjectsByUserUuid(userUuid)
            withContext(Dispatchers.Main){
                projectList.value = temp
                projectList.value = projectList.value?.sortedByDescending { it.priority }
            }
        }
    }

    fun deleteProject(projectUuid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            projectApi.deleteProject(projectUuid)
            val projects = projectList.value?.toMutableList() // Копирование списка в изменяемый список
            projects?.removeIf { it.uuid == projectUuid } // Удаление проекта с заданным UUID

            withContext(Dispatchers.Main) {
                projectList.value = projects!! // Установка обновленного списка в projectList
            }
        }
    }

    fun changeProjectPriority(projectUuid: String){
        CoroutineScope(Dispatchers.IO).launch {
            projectApi.changeProjectPriority(projectUuid)
        }
        projectList.value = projectList.value?.map {
            if (it.uuid == projectUuid) {
                it.copy(priority = !(it.priority!!))
            } else {
                it
            }
        }
        projectList.value = projectList.value?.sortedByDescending { it.priority }
    }

    fun createAdapter(projects: List<ProjectData>) : ProjectListAdapter{
        return ProjectListAdapter(projects, this)
    }
}
