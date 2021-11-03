package com.salanevich.testapp.vm.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salanevich.testapp.cache.filter.Filter
import com.salanevich.testapp.cache.filter.FilterCache

class SearchInViewModel(private val filter: FilterCache): ViewModel() {

    private val _searchIn = MutableLiveData<List<Filter.SearchIn>>()
    val searchIn: LiveData<List<Filter.SearchIn>> = _searchIn
    private var set : MutableSet<Filter.SearchIn> = mutableSetOf()

    fun initData() {
        val list = filter.getSearchInItems()
        _searchIn.value = list
        set = list.toMutableSet()
    }

    fun putTitle() {
        set.add(Filter.SearchIn.TITLE)
    }

    fun removeTitle() {
        set.remove(Filter.SearchIn.TITLE)
    }

    fun putDesc() {
        set.add(Filter.SearchIn.DESC)
    }

    fun removeDesc() {
        set.remove(Filter.SearchIn.DESC)
    }

    fun putContent() {
        set.add(Filter.SearchIn.CONTENT)
    }

    fun removeContent() {
        set.remove(Filter.SearchIn.CONTENT)
    }

    fun clearData() {
        set.clear()
    }

    fun applyData() {
        filter.setSearchIn(set)
    }

}