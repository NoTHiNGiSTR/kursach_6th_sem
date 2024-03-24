package com.example.kursach_6th_sem.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.databinding.ProjectItemBinding
import com.example.kursach_6th_sem.databinding.TaskItemBinding

class TaskListHolder(item: View): RecyclerView.ViewHolder(item) {
    val context = item
    val binding = TaskItemBinding.bind(item)
    fun bind() = with(binding){
        binding.taskName.text = "taskName"
        binding.taskDescription.text="task task task task task task task task task task task task task task task task task task task task v task task task task"
        binding.taskDeadline.text="00.00.0000"
        binding.taskStatus.text="В работе"
    }
}