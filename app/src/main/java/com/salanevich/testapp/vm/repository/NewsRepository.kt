package com.salanevich.testapp.vm.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.salanevich.testapp.model.NewsModel
import com.salanevich.testapp.network.datasource.NEWS_PAGE_SIZE
import com.salanevich.testapp.network.datasource.NewsDataSource
import kotlinx.coroutines.flow.Flow

class NewsRepository(source: NewsDataSource) {

    private val pager = Pager(
        config = PagingConfig(NEWS_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { source }
    )

    fun fetch(): Flow<PagingData<NewsModel>> {
        return pager.flow
    }

}