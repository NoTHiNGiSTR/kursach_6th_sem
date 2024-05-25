package com.example.kursach_6th_sem

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.kursach_6th_sem.sharedPreference.SharedPrefs
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory


//@HiltAndroidApp
class App : Application() {

    companion object{
        lateinit var retrofit: Retrofit
    }


    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder().baseUrl("http://192.168.52.52:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val sharedPreferences = getSharedPreferences("theme_pref", Context.MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


}