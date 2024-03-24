package com.example.kursach_6th_sem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.R

class TaskListAdapter(): RecyclerView.Adapter<TaskListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskListHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: TaskListHolder, position: Int) {
        holder.bind()

        holder.itemView.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_projectFragment_to_taskFragment)
        }
    }
}