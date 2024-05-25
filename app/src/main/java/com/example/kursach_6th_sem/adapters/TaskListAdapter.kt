package com.example.kursach_6th_sem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.R
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.api.entities.TaskData
import com.example.kursach_6th_sem.api.models.TaskViewModel

class TaskListAdapter(private var taskList: List<TaskData>, private  val viewModel: TaskViewModel): RecyclerView.Adapter<TaskListHolder>() {

    var onItemClick : ((TaskData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskListHolder(view, viewModel)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskListHolder, position: Int) {
        val item = taskList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(item)
        }
    }
}