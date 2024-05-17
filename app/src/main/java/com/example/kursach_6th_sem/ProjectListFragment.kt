package com.example.kursach_6th_sem

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursach_6th_sem.adapters.ProjectListAdapter
import com.example.kursach_6th_sem.databinding.FragmentProjectListBinding
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.adapters.RecentAdapter
import com.example.kursach_6th_sem.api.entities.ProjectData
import com.example.kursach_6th_sem.api.models.ProjectViewModel
import com.example.kursach_6th_sem.searchConfig.SearchHistoryManager
import com.example.kursach_6th_sem.sharedPreference.SharedPrefs
import java.util.Locale


class ProjectListFragment : Fragment() {

    private var _binding: FragmentProjectListBinding? = null
    private val binding: FragmentProjectListBinding get() = _binding!!
    private val projectViewModel: ProjectViewModel by viewModels()
    private var mList = listOf<ProjectData>()
    private lateinit var adapter: ProjectListAdapter
    private lateinit var recentAdapter: RecentAdapter
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProjectListBinding.inflate(inflater, container, false)


        binding.toProfileFromProjectListFragment.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_projectListFragment_to_profileFragment)
        }





        return binding.root
    }

    fun Fragment.hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun filterList(query : String?){
        if (query!=null){
            val filteredList = ArrayList<ProjectData>()
            for (i in mList){
                if (i.projectName!!.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isNotEmpty()) {
                binding.projectList.adapter = projectViewModel.createAdapter(filteredList)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val searchItem = binding.plSearchView
        val searchView = binding.plSearchView as SearchView
        searchView.queryHint = "Поиск"


        recentAdapter = RecentAdapter {
            binding.plSearchView
        }

        val sharedPreference = SharedPrefs(requireActivity())
        val queries = sharedPreference.getList("recent")

        searchView.setOnQueryTextFocusChangeListener{ view, hasFocus ->
            if (hasFocus){
                binding.searchHistoryPlaceholder.visibility = VISIBLE
                binding.projectList.visibility = GONE

                if (queries.isNotEmpty())
                    recentAdapter.recentQuerries = sharedPreference.getList("recent")
                else
                    recentAdapter.recentQuerries = listOf(" ")
                val layoutManagerRecent =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.searchHistory.layoutManager = layoutManagerRecent
                binding.searchHistory.adapter = recentAdapter
            }
            else{
                binding.searchHistoryPlaceholder.visibility = GONE
                binding.projectList.visibility = VISIBLE
            }
        }

        binding.clearSearchHistory.setOnClickListener{
            sharedPreference.saveList("recent", emptyList<String>())
            recentAdapter.updateData(emptyList())
            recentAdapter.notifyDataSetChanged()
        }

        binding.plSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val tmp = sharedPreference.getList("recent").toMutableList()
                if (query != null) {
                    if (tmp.size == 10){
                        tmp.removeAt(9)
                    }
                    tmp.add(0, query)
                }
                sharedPreference.saveList("recent", tmp.toList())
                recentAdapter.updateData(tmp)
                recentAdapter.notifyDataSetChanged()
                binding.searchHistoryPlaceholder.visibility = GONE
                binding.projectList.visibility = VISIBLE
                hideKeyboard()
                binding.plSearchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })


        binding.projectList.layoutManager = LinearLayoutManager(context)
        projectViewModel.projectList.observe(this as LifecycleOwner){
            binding.projectList.adapter = projectViewModel.createAdapter(it)
            adapter = projectViewModel.createAdapter(it)
            mList = it
        }

        sharedPreferences = requireActivity().getSharedPreferences("theme_pref", Context.MODE_PRIVATE)

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


        projectViewModel.getAllProject()
    }


    companion object {
        // 30 тысяч миллисекунд == 30 секунд
        private const val REFRESH_LIST_DELAY_MILLIS = 30_000L
    }

    private var mainThreadHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainThreadHandler = Handler(Looper.getMainLooper())
    }

    override fun onStart() {
        super.onStart()

        mainThreadHandler?.postDelayed(
            object : Runnable {
                override fun run() {
                    // Обновляем список в главном потоке
                    refreshList()

                    // И снова планируем то же действие через 30 секунд
                    mainThreadHandler?.postDelayed(
                        this,
                        REFRESH_LIST_DELAY_MILLIS,
                    )
                }
            },
            REFRESH_LIST_DELAY_MILLIS
        )
    }
    private fun refreshList() {
        binding.progressBar.visibility = VISIBLE
        binding.projectList.visibility = GONE
        mainThreadHandler?.postDelayed(
            {
                projectViewModel.getAllProject()
                binding.progressBar.visibility = GONE
                binding.projectList.visibility = VISIBLE
            },3000)
    }





    private fun toggleTheme() {
        val currentNightMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            android.content.res.Configuration.UI_MODE_NIGHT_NO -> {
                // Сейчас используется светлая тема, переключаем на тёмную
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            android.content.res.Configuration.UI_MODE_NIGHT_YES -> {
                // Сейчас используется тёмная тема, переключаем на светлую
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}