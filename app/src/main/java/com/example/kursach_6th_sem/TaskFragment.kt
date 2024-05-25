package com.example.kursach_6th_sem

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.api.entities.TaskData
import com.example.kursach_6th_sem.databinding.FragmentTaskBinding
import com.example.kursach_6th_sem.databinding.FragmentTaskConstructorBinding
import com.example.kursach_6th_sem.sharedPreference.SharedPrefs


class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding: FragmentTaskBinding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        val sharedPreference = SharedPrefs(requireActivity())

        val task = arguments?.getParcelable<TaskData>("task")
        val project = arguments?.getParcelable<ProjectData>("project")
        System.out.println(project)


        binding.taskFragmentName.text = task!!.taskName
        binding.taskFragmentDescription.text = task!!.description
        binding.taskFragmentStatus.text = task!!.status
        binding.taskFragmentDeadline.text = task!!.deadline

        binding.backToProjectFragment.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.taskFragmentEditTask.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("taskToEdit", task)
            bundle.putParcelable("project", project)
            Navigation.findNavController(it).navigate(R.id.action_taskFragment_to_taskConstructor, bundle)
        }

        binding.toProfile.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_taskFragment_to_profileFragment)
        }

        binding.toProjects.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_taskFragment_to_projectListFragment)
        }

        binding.exitButton.setOnClickListener {
            sharedPreference.clearSharedPreference()
            findNavController().navigate(R.id.action_taskFragment_to_projectListFragment)
        }

        val sharedPreferences = requireActivity()
            .getSharedPreferences("theme_pref", Context.MODE_PRIVATE)

        binding.themeSwitcher.setOnClickListener {
            val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)
            if (isDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("isDarkTheme", false).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("isDarkTheme", true).apply()
            }
        }

        return binding.root
    }

}