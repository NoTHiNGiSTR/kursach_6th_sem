package com.example.kursach_6th_sem.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.databinding.ProjectItemBinding

class ProjectListHolder(item : View): RecyclerView.ViewHolder(item) {
    val context = item
    val binding = ProjectItemBinding.bind(item)
    fun bind(projectData: ProjectData) = with(binding){
        binding.projectName.text = projectData.projectName
        binding.projectDescription.text = projectData.description
        binding.projectStatus.text= projectData.status
    }
}