package com.example.kursach_6th_sem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursach_6th_sem.adapters.ProjectListAdapter
import com.example.kursach_6th_sem.adapters.TaskListAdapter
import com.example.kursach_6th_sem.databinding.FragmentProjectBinding
import com.example.kursach_6th_sem.databinding.FragmentProjectListBinding


class ProjectFragment : Fragment() {

    private var _binding: FragmentProjectBinding? = null
    private val binding: FragmentProjectBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)

        binding.TaskList.adapter = TaskListAdapter()
        binding.TaskList.layoutManager = LinearLayoutManager(requireContext())




        return binding.root
    }

}