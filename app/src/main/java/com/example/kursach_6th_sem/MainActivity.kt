package com.example.kursach_6th_sem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kursach_6th_sem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}