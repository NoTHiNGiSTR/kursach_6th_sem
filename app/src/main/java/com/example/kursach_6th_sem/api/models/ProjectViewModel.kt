package com.example.kursach_6th_sem.api.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.kursach_6th_sem.App
import com.example.kursach_6th_sem.adapters.ProjectListAdapter
import com.example.kursach_6th_sem.api.Api
import com.example.kursach_6th_sem.api.entities.ProjectData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class ProjectViewModel  : ViewModel() {
    val projectList = MutableLiveData<List<ProjectData>>()

    val projectApi = App.retrofit.create(Api::class.java)

    fun getAllProject(){
        CoroutineScope(Dispatchers.IO).launch {
            val temp = projectApi.getAllProjects()
            withContext(Dispatchers.Main){
                projectList.value = temp
            }
        }
    }

    fun createAdapter(projects: List<ProjectData>) : ProjectListAdapter{
        return ProjectListAdapter(projects)
    }
}
