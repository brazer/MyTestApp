package com.salanevich.testapp.vm.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.salanevich.testapp.cache.filter.Filter
import com.salanevich.testapp.cache.sorting.Sorting
import com.salanevich.testapp.model.NewsModel
import com.salanevich.testapp.network.datasource.NEWS_PAGE_SIZE
import com.salanevich.testapp.network.datasource.SearchDataSource
import kotlinx.coroutines.flow.Flow

class SearchRepository(private val source: SearchDataSource) {

    fun search(query: String, filter: Filter, sorting: Sorting.Parameter): Flow<PagingData<NewsModel>> {
        source.query = query
        source.filter = filter
        source.sorting = sorting
        return Pager(
            config = PagingConfig(NEWS_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { source }
        ).flow
    }

}