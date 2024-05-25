package com.example.kursach_6th_sem

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursach_6th_sem.adapters.ProjectListAdapter
import com.example.kursach_6th_sem.adapters.RecentAdapter
import com.example.kursach_6th_sem.adapters.TaskListAdapter
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.api.entities.TaskData
import com.example.kursach_6th_sem.api.models.TaskViewModel
import com.example.kursach_6th_sem.databinding.FragmentProjectBinding
import com.example.kursach_6th_sem.databinding.FragmentProjectListBinding
import com.example.kursach_6th_sem.sharedPreference.SharedPrefs
import java.util.Locale


class ProjectFragment : Fragment() {

    val bundle = Bundle()
    private var _binding: FragmentProjectBinding? = null
    private val binding: FragmentProjectBinding get() = _binding!!
    private val taskViewModel: TaskViewModel by viewModels()
    private var mList = listOf<TaskData>()
    private lateinit var adapter: TaskListAdapter
    private lateinit var recentAdapter: RecentAdapter
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)

        val item = arguments?.getParcelable<ProjectData>("project")

        val sharedPreference = SharedPrefs(requireActivity())
        val queries = sharedPreference.getList("recent_tasks")

        taskViewModel.getProjectTasks(item!!.uuid!!)

        binding.ProjectFragmentHeader.text = "   " + item.projectName

        binding.TaskList.layoutManager = LinearLayoutManager(context)
        val navController = findNavController()
        taskViewModel.taskList.observe(this as LifecycleOwner){
            it ->
            adapter = taskViewModel.createAdapter(it)
            binding.TaskList.adapter = adapter
            mList = it
            adapter.onItemClick = {
                val bundle = Bundle()
                bundle.putParcelable("task", it)
                bundle.putParcelable("project", item)
                navController.navigate(R.id.action_projectFragment_to_taskFragment, bundle)
            }
        }

        val searchView = binding.plSearchView
        searchView.queryHint = "Поиск"

        sharedPreferences = requireActivity().
            getSharedPreferences("theme_pref", Context.MODE_PRIVATE)


        recentAdapter = RecentAdapter {
            binding.plSearchView
        }

        binding.ProjectFragmentAddButton.setOnClickListener {
            val nbundle = Bundle()
            nbundle.putParcelable("project", item)
            System.out.println(item!!.uuid)
            Navigation.findNavController(it).navigate(R.id.action_projectFragment_to_taskConstructor, nbundle)
        }

        searchView.setOnQueryTextFocusChangeListener{ view, hasFocus ->
            if (hasFocus){
                binding.searchHistoryPlaceholder.visibility = View.VISIBLE
                binding.TaskList.visibility = View.GONE

                if (queries.isNotEmpty())
                    recentAdapter.recentQuerries = sharedPreference.getList("recent_tasks")
                else
                    recentAdapter.recentQuerries = listOf(" ")
                val layoutManagerRecent =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.searchHistory.layoutManager = layoutManagerRecent
                binding.searchHistory.adapter = recentAdapter
            }
            else{
                binding.searchHistoryPlaceholder.visibility = View.GONE
                binding.TaskList.visibility = View.VISIBLE
            }
        }

        binding.clearSearchHistory.setOnClickListener{
            sharedPreference.saveList("recent_tasks", emptyList<String>())
            recentAdapter.updateData(emptyList())
            recentAdapter.notifyDataSetChanged()
        }

        binding.ProjectFragmentExitButton.setOnClickListener {
            sharedPreference.clearSharedPreference()
            findNavController().navigate(R.id.action_projectFragment_to_projectListFragment)
        }

        binding.plSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val tmp = sharedPreference.getList("recent_tasks").toMutableList()
                if (query != null) {
                    if (tmp.size == 10){
                        tmp.removeAt(9)
                    }
                    tmp.add(0, query)
                }
                sharedPreference.saveList("recent_tasks", tmp.toList())
                recentAdapter.updateData(tmp)
                recentAdapter.notifyDataSetChanged()
                binding.searchHistoryPlaceholder.visibility = View.GONE
                binding.TaskList.visibility = View.VISIBLE
                hideKeyboard()
                binding.plSearchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })






        return binding.root
    }

    private fun filterList(query : String?){
        if (query!=null){
            val filteredList = ArrayList<TaskData>()
            for (i in mList){
                if (i.taskName!!.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isNotEmpty()) {
                binding.TaskList.adapter = taskViewModel.createAdapter(filteredList)
            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toProfileFromProjectFragment.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_projectFragment_to_profileFragment)
        }




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

        binding.ProjectFragmentBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.toProjects.setOnClickListener {
            findNavController().navigate(R.id.action_projectFragment_to_projectListFragment)
        }


    }

    fun Fragment.hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}