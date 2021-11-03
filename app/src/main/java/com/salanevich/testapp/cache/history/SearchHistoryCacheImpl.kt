package com.salanevich.testapp.cache.history

class SearchHistoryCacheImpl: SearchHistoryCache {

    private val cache = mutableListOf<String>()

    override fun addQuery(query: String) {
        cache.add(query)
    }

    override fun getQueries(): List<String> {
        return cache
    }

}