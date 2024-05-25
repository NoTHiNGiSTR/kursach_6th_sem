package com.example.kursach_6th_sem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.kursach_6th_sem.api.Api
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.api.entities.TaskData
import com.example.kursach_6th_sem.databinding.FragmentProjectConstructorBinding
import com.example.kursach_6th_sem.sharedPreference.SharedPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProjectConstructor : Fragment() {

    private var _binding: FragmentProjectConstructorBinding? = null
    private val binding: FragmentProjectConstructorBinding get() = _binding!!
    private lateinit var sharedPrefs: SharedPrefs
    val projectApi = App.retrofit.create(Api::class.java)
    private var status = ""
    private val statuses = listOf<String>("Не начат", "В работе", "Завершён")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentProjectConstructorBinding.inflate(inflater, container, false)

        binding.projectConstructorStatus.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.task_status, android.R.layout.simple_spinner_dropdown_item)




        val item = arguments?.getParcelable<ProjectData>("project")
        if (item != null){
            binding.projectConstructorName.setText(item.projectName)
            binding.projectConstructorDescription.setText(item.description)

            binding.createProjectButton.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    editProject(item)
                    withContext(Dispatchers.Main){
                        val bundle = Bundle()
                        bundle.putParcelable("project", item)
                        Navigation.findNavController(it).navigate(R.id.action_projectConstructor_to_projectFragment, bundle)
                    }
                }
            }
        }
        else {
            binding.createProjectButton.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    val newProject = createProject()
                    withContext(Dispatchers.Main){
                        val bundle = Bundle()
                        bundle.putParcelable("project", newProject)
                        Navigation.findNavController(it).navigate(R.id.action_projectConstructor_to_projectFragment, bundle)
                    }
                }

            }
        }


        binding.projectConstructorStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                status = statuses[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return binding.root
    }


    private suspend fun createProject() : ProjectData?{
        sharedPrefs = SharedPrefs(requireContext())
        var projectName = binding.projectConstructorName.text.toString()
        var projectDescription = binding.projectConstructorDescription.text.toString()
        var projectData = ProjectData(uuid = null, projectName = projectName, description = projectDescription, status = status, priority = false, taskList=emptyList<TaskData>(), userUuid=sharedPrefs.getPreferenceString("uuid"))

        return projectApi.createProject(projectData)
    }

    private suspend fun editProject(edited: ProjectData) : ProjectData?{
        sharedPrefs = SharedPrefs(requireContext())
        var projectName = binding.projectConstructorName.text.toString()
        var projectDescription = binding.projectConstructorDescription.text.toString()

        var projectData = ProjectData(uuid = edited.uuid, projectName = projectName, description = projectDescription, status = status, priority = false, taskList=edited.taskList, userUuid=edited.userUuid)

        return projectApi.editProject(projectData)
    }


}