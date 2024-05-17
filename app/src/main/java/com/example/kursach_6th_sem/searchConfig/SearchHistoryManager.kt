package com.example.kursach_6th_sem.searchConfig

import android.content.Context

class SearchHistoryManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)

    fun getSearchHistory(): List<String> {
        return sharedPreferences.getStringSet("search_history", setOf())?.toList().orEmpty()
    }

    fun addSearchQuery(query: String) {
        val history = getSearchHistory().toMutableSet()
        history.add(query)
        sharedPreferences.edit().putStringSet("search_history", history).apply()
    }

    fun clearSearchHistory() {
        sharedPreferences.edit().remove("search_history").apply()
    }
}