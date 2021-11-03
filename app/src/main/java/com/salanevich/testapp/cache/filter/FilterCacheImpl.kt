package com.salanevich.testapp.cache.filter

import java.util.HashSet

class FilterCacheImpl: FilterCache {

    private val cache = Filter()

    override fun setDateFrom(date: Long?) {
        cache.dateFrom = date
    }

    override fun getDateFrom(): Long? = cache.dateFrom

    override fun setDateTo(date: Long?) {
        cache.dateTo = date
    }

    override fun getDateTo(): Long? = cache.dateTo

    override fun addSearchInItem(value: Filter.SearchIn) {
        cache.searchIn.add(value)
    }

    override fun removeSearchInItem(value: Filter.SearchIn) {
        cache.searchIn.remove(value)
    }

    override fun clearSearchInItems() {
        cache.searchIn.clear()
    }

    override fun addAllSearchInItems() {
        addSearchInItem(Filter.SearchIn.TITLE)
        addSearchInItem(Filter.SearchIn.DESC)
        addSearchInItem(Filter.SearchIn.CONTENT)
    }

    override fun getSearchInItems(): List<Filter.SearchIn> = cache.searchIn.toList()

    override fun getFilter(): Filter = cache

    override fun setSearchIn(set: Set<Filter.SearchIn>) {
        cache.searchIn = set as HashSet<Filter.SearchIn>
    }

}