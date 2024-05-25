package com.example.kursach_6th_sem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kursach_6th_sem.databinding.FragmentProfileBinding
import com.example.kursach_6th_sem.databinding.FragmentProjectConstructorBinding
import com.example.kursach_6th_sem.sharedPreference.SharedPrefs

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val sharedPreference = SharedPrefs(requireActivity())


        binding.Email.setText(sharedPreference.getPreferenceString("email"))
        binding.Nickname.setText(sharedPreference.getPreferenceString("name"))


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.toProjects.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_projectListFragment)
        }

        binding.exitButton.setOnClickListener {
            sharedPreference.clearSharedPreference()
            findNavController().navigate(R.id.action_profileFragment_to_authorizationFragment)
        }

        return binding.root
    }
}