package com.salanevich.testapp.cache.history

interface SearchHistoryCache {
    fun addQuery(query: String)
    fun getQueries(): List<String>
}