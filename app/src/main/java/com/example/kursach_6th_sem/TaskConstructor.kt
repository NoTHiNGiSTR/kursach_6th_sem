package com.example.kursach_6th_sem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.Navigation
import com.example.kursach_6th_sem.api.Api
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.api.entities.TaskData
import com.example.kursach_6th_sem.databinding.FragmentTaskConstructorBinding
import com.example.kursach_6th_sem.sharedPreference.SharedPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class TaskConstructor : Fragment() {

    private var _binding: FragmentTaskConstructorBinding? = null
    private val binding: FragmentTaskConstructorBinding get() = _binding!!
    private lateinit var sharedPrefs: SharedPrefs
    val projectApi = App.retrofit.create(Api::class.java)
    private var status = "Не начат"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTaskConstructorBinding.inflate(inflater, container, false)

        val taskToEdit = arguments?.getParcelable<TaskData>("taskToEdit")
        val project = arguments?.getParcelable<ProjectData>("project")
        val statuses = listOf<String>("Не начат", "В работе", "Завершён")



        if (taskToEdit != null){
            binding.taskConstructorName.setText(taskToEdit.taskName)
            binding.taskConstructorDescription.setText(taskToEdit.description)
            status = taskToEdit.status!!
            binding.deadlineCalendar.date = convertStringToDate(taskToEdit.deadline!!).time
        }

        val calendarView = binding.deadlineCalendar
        var x1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var x2 = Calendar.getInstance().get(Calendar.MONTH) + 1
        var x3 = Calendar.getInstance().get(Calendar.YEAR)

        calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            // Вызывается при изменении выбранной даты
            x1 = day
            x2 = month+1
            x3 = year
        }

        var name = binding.taskConstructorName.text
        var description = binding.taskConstructorDescription.text
        var deadline = x1.toString() + "/" + x2.toString() +"/" +x3.toString()


        binding.projectConstructorStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                status = statuses[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        binding.createTaskButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val task = TaskData(uuid = null, taskName = name.toString(), description= description.toString(), status= status, priority= false, deadline= deadline, projectUuid= project!!.uuid)
                val newTask = projectApi.createTask(task)
                withContext(Dispatchers.Main){
                    val bundle = Bundle()
                    bundle.putParcelable("task", newTask)
                    Navigation.findNavController(it).navigate(R.id.action_taskConstructor_to_taskFragment, bundle)
                }
            }
        }


        val item = arguments?.getParcelable<TaskData>("taskToEdit")
        if (item != null){

            binding.createTaskButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val task = TaskData(uuid = item.uuid, taskName = name.toString(), description= description.toString(), status= status.toString(), priority= false, deadline= deadline, projectUuid= item.projectUuid)
                    val newTask = projectApi.editTask(task)
                    withContext(Dispatchers.Main){
                        val bundle = Bundle()
                        bundle.putParcelable("task", newTask)
                        Navigation.findNavController(it).navigate(R.id.action_taskConstructor_to_taskFragment, bundle)
                    }
                }
            }
        }
        else {
            binding.createTaskButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val task = TaskData(uuid = null, taskName = name.toString(), description= description.toString(), status= status.toString(), priority= false, deadline= deadline, projectUuid= project!!.uuid)
                    val newTask = projectApi.createTask(task)
                    withContext(Dispatchers.Main){
                        val bundle = Bundle()
                        bundle.putParcelable("task", newTask)
                        Navigation.findNavController(it).navigate(R.id.action_taskConstructor_to_taskFragment, bundle)
                    }
                }
            }
        }
        return binding.root
    }

    fun convertStringToDate(dateString: String): Date {
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.parse(dateString)
    }

}