package com.example.kursach_6th_sem.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.databinding.ProjectItemBinding

class ProjectListHolder(item : View): RecyclerView.ViewHolder(item) {
    val context = item
    val binding = ProjectItemBinding.bind(item)
    fun bind() = with(binding){
        binding.projectName.text = "Название"
        binding.projectDescription.text = "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание"
        binding.projectStatus.text= "В работе"
    }
}