package com.example.kursach_6th_sem.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import com.example.kursach_6th_sem.api.entities.UserData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefs(val context: Context){
    private val preferenceName = "noth1ng_1s_true"
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
    var sharedUser: UserData? = null
    private val gson = Gson()

    /*Stored String Data*/
    fun saveString(key_name: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key_name, text)
        editor.apply()
    }
    fun saveLong(key_name: String, text: Long) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(key_name, text)
        editor.apply()
    }
    fun saveBool(key_name: String, text: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key_name, text)
        editor.apply()
    }

    fun getString(key_name: String): String? {
        return sharedPreferences.getString(key_name, null)
    }
    fun getLong(key_name: String): Long {
        return sharedPreferences.getLong(key_name, 0)
    }
    fun getBool(key_name: String): Boolean {
        return sharedPreferences.getBoolean(key_name,false)
    }
    fun getList(key: String): List<Any> {
        val json = sharedPreferences.getString(key, null)
        val type = object : TypeToken<List<Any>>() {}.type
        return gson.fromJson(json, type) ?: listOf()
    }
    fun saveList(key: String, list: List<Any>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }


    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


    fun saveUser(uuid: String, name: String, email: String, key_name: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key_name, text)
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_UUID, uuid)
        editor.apply()
    }

    //объект хранящий данные залогиненного пользователя
    companion object{
        val IS_LOGIN = "isLoggedIn"
        val KEY_NAME = "name"
        val KEY_EMAIL = "email"
        val KEY_UUID = "uuid"
    }

    fun getPreferenceString(key_name: String): String? {
        return sharedPreferences.getString(key_name, null)
    }
}