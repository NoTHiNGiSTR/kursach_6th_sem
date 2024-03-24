package com.example.kursach_6th_sem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursach_6th_sem.adapters.ProjectListAdapter
import com.example.kursach_6th_sem.databinding.FragmentProjectListBinding


class ProjectListFragment : Fragment() {

    private var _binding: FragmentProjectListBinding? = null
    private val binding: FragmentProjectListBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProjectListBinding.inflate(inflater, container, false)

        binding.projectList.adapter = ProjectListAdapter()
        binding.projectList.layoutManager = LinearLayoutManager(requireContext())

        binding.toProfileFromProjectListFragment.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_projectListFragment_to_profileFragment)
        }



        return binding.root
    }

}