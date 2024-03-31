package com.example.kursach_6th_sem

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursach_6th_sem.adapters.ProjectListAdapter
import com.example.kursach_6th_sem.databinding.FragmentProjectListBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard


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

        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty()){
                    binding.clearSearchField.visibility = View.INVISIBLE
                } else {
                    binding.clearSearchField.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.clearSearchField.setOnClickListener {
            binding.searchField.text.clear()
            binding.clearSearchField.visibility = View.INVISIBLE
            hideKeyboard()
        }

        binding.searchField.setOnEditorActionListener { v, actionId, event ->
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
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


}