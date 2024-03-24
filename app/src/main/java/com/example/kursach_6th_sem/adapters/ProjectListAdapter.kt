package com.example.kursach_6th_sem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.R

class ProjectListAdapter(): RecyclerView.Adapter<ProjectListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ProjectListHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ProjectListHolder, position: Int) {
        holder.bind()

        holder.itemView.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_projectListFragment_to_projectFragment)
        }
    }

}