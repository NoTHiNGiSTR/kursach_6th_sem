package com.example.kursach_6th_sem.api.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kursach_6th_sem.App
import com.example.kursach_6th_sem.adapters.TaskListAdapter
import com.example.kursach_6th_sem.api.Api
import com.example.kursach_6th_sem.api.entities.TaskData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.create

open class TaskViewModel : ViewModel() {
    val taskList = MutableLiveData<List<TaskData>>()
    val taskApi = App.retrofit.create(Api::class.java)
    val projectApi = App.retrofit.create(Api::class.java)

    fun getProjectTasks(projectUuid: String){
        CoroutineScope(Dispatchers.IO).launch {
            val temp = taskApi.getProjectTasks(projectUuid)
            withContext(Dispatchers.Main){
                taskList.value = temp
                taskList.value = taskList.value?.sortedByDescending { it.priority }
            }
        }
    }

    fun deleteTask(taskUuid: String, projectUuid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            projectApi.deleteTask(taskUuid, projectUuid)
            val projects = taskList.value?.toMutableList() // Копирование списка в изменяемый список
            projects?.removeIf { it.uuid == taskUuid } // Удаление проекта с заданным UUID

            withContext(Dispatchers.Main) {
                taskList.value = projects!! // Установка обновленного списка в projectList
            }
        }
    }

    fun changeTaskPriority(taskUuid: String){
        CoroutineScope(Dispatchers.IO).launch {
            projectApi.changeTaskPriority(taskUuid)
        }
        taskList.value = taskList.value?.map {
            if (it.uuid == taskUuid) {
                it.copy(priority = !(it.priority!!))
            } else {
                it
            }
        }
        taskList.value = taskList.value?.sortedByDescending { it.priority }
    }

    fun createAdapter(tasks: List<TaskData>) : TaskListAdapter{
        return TaskListAdapter(tasks, this)
    }

}