package com.salanevich.testapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.salanevich.testapp.cache.filter.Filter
import com.salanevich.testapp.cache.filter.FilterCache
import com.salanevich.testapp.cache.history.SearchHistoryCache
import com.salanevich.testapp.cache.sorting.Sorting
import com.salanevich.testapp.cache.sorting.SortingCache
import com.salanevich.testapp.model.NewsModel
import com.salanevich.testapp.vm.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val searchHistoryCache: SearchHistoryCache,
    private val filterCache: FilterCache,
    private val sortingCache: SortingCache,
    private val repository: SearchRepository
) : ViewModel() {

    private val _history = MutableLiveData<List<String>>()
    val history: LiveData<List<String>> = _history
    private val _filter = MutableLiveData<Filter>()
    val filter: LiveData<Filter> = _filter
    private val _sorting = MutableLiveData<Sorting.Parameter>()
    val sorting: LiveData<Sorting.Parameter> = _sorting

    fun loadHistory() {
        _history.value = searchHistoryCache.getQueries()
    }

    fun loadFilter() {
        _filter.value = filterCache.getFilter()
    }

    fun loadSorting() {
        _sorting.value = sortingCache.getParameter()
    }

    fun search(query: String): Flow<PagingData<NewsModel>> {
        return repository.search(
            query,
            filterCache.getFilter(),
            sortingCache.getParameter()
        ).cachedIn(viewModelScope)
    }

    fun addToHistory(query: String) {
        searchHistoryCache.addQuery(query)
    }

    fun setSortingParameter(parameter: Sorting.Parameter) {
        sortingCache.setParameter(parameter)
    }

}