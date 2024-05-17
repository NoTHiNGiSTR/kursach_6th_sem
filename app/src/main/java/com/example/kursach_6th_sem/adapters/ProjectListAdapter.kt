package com.example.kursach_6th_sem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.R
import com.example.kursach_6th_sem.api.entities.ProjectData

class ProjectListAdapter(private var projectList: List<ProjectData>): RecyclerView.Adapter<ProjectListHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ProjectListHolder(view)
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    fun setFilteredList(projectList: List<ProjectData>){
        this.projectList = projectList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProjectListHolder, position: Int) {
        val item = projectList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_projectListFragment_to_projectFragment)
        }
    }

}