package com.salanevich.testapp.cache.sorting

class SortingCacheImpl: SortingCache {

    private val cache = Sorting()

    override fun getParameter(): Sorting.Parameter {
        return cache.parameter
    }

    override fun setParameter(parameter: Sorting.Parameter) {
        cache.parameter = parameter
    }

}