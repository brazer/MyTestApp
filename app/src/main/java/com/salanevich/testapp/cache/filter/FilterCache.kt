package com.salanevich.testapp.cache.filter

interface FilterCache {
    fun setDateFrom(date: Long?)
    fun getDateFrom(): Long?
    fun setDateTo(date: Long?)
    fun getDateTo(): Long?
    fun addSearchInItem(value: Filter.SearchIn)
    fun removeSearchInItem(value: Filter.SearchIn)
    fun clearSearchInItems()
    fun addAllSearchInItems()
    fun getSearchInItems(): List<Filter.SearchIn>
    fun getFilter(): Filter
    fun setSearchIn(set: Set<Filter.SearchIn>)
}