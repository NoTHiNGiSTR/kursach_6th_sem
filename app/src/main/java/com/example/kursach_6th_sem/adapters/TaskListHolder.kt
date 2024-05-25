package com.example.kursach_6th_sem.adapters

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.R
import com.example.kursach_6th_sem.api.entities.TaskData
import com.example.kursach_6th_sem.api.models.TaskViewModel
import com.example.kursach_6th_sem.databinding.ProjectItemBinding
import com.example.kursach_6th_sem.databinding.TaskItemBinding

class TaskListHolder(item: View, private val viewModel: TaskViewModel): RecyclerView.ViewHolder(item) {
    val context = item
    val binding = TaskItemBinding.bind(item)
    fun bind(taskData: TaskData) = with(binding){
        binding.taskName.text = taskData.taskName
        binding.taskDescription.text = taskData.description
        binding.taskDeadline.text = taskData.deadline.toString() //TODO: Сделать перевод DATe в строку
        binding.taskStatus.text = taskData.status
        if(taskData.priority == true){
            binding.favoriteButton.setImageResource(android.R.drawable.star_on)
        }
        else binding.favoriteButton.setImageResource(android.R.drawable.star_off)

        binding.removeTask.setOnClickListener {
            viewModel.deleteTask(taskData.uuid!!, taskData.projectUuid!!)
        }

        binding.favoriteButton.setOnClickListener {
            viewModel.changeTaskPriority(taskData.uuid!!)
        }

        binding.editTask.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("taskToEdit", taskData)
            Navigation.findNavController(it)
                .navigate(R.id.action_projectFragment_to_taskConstructor, bundle)
        }
    }


}