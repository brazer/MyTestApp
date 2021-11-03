package com.salanevich.testapp.cache.sorting

interface SortingCache {
    fun getParameter(): Sorting.Parameter
    fun setParameter(parameter: Sorting.Parameter)
}