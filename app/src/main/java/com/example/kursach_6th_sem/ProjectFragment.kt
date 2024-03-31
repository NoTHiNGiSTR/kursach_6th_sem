package com.example.kursach_6th_sem

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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

        binding.taskSearchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty()){
                    binding.clearTaskSearchField.visibility = View.INVISIBLE
                } else {
                    binding.clearTaskSearchField.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.clearTaskSearchField.setOnClickListener {
            binding.taskSearchField.text.clear()
            binding.clearTaskSearchField.visibility = View.INVISIBLE
            hideKeyboard()

        }

        binding.taskSearchField.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            } else {
                false
            }
        }



        return binding.root
    }

    fun Fragment.hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}