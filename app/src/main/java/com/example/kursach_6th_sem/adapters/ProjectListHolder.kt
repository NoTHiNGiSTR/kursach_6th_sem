package com.example.kursach_6th_sem.adapters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.R
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.api.models.ProjectViewModel
import com.example.kursach_6th_sem.databinding.ProjectItemBinding

class ProjectListHolder(item : View, private val viewModel: ProjectViewModel): RecyclerView.ViewHolder(item) {
    val context = item
    val binding = ProjectItemBinding.bind(item)
    fun bind(projectData: ProjectData) = with(binding){
        binding.projectName.text = projectData.projectName
        binding.projectDescription.text = projectData.description
        binding.projectStatus.text= projectData.status
        if(projectData.priority == true){
            binding.favoriteButton.setImageResource(android.R.drawable.star_on)
        }
        else binding.favoriteButton.setImageResource(android.R.drawable.star_off)

        binding.editProject.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("project", projectData)
            Navigation.findNavController(it)
                .navigate(R.id.action_projectListFragment_to_projectConstructor, bundle)
        }

        binding.removeProject.setOnClickListener {
            viewModel.deleteProject(projectData.uuid!!)
        }

        binding.favoriteButton.setOnClickListener {
            viewModel.changeProjectPriority(projectData.uuid!!)
        }
    }

}