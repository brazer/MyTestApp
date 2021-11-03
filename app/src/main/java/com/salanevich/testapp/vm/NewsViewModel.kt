package com.salanevich.testapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.salanevich.testapp.model.NewsModel
import com.salanevich.testapp.vm.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsViewModel(
    private val repository: NewsRepository
): ViewModel() {

    fun fetchData(): Flow<PagingData<NewsModel>> {
        return repository.fetch().cachedIn(viewModelScope)
    }

}