package com.salanevich.testapp.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.salanevich.testapp.model.NewsModel
import com.salanevich.testapp.model.map
import com.salanevich.testapp.network.response.NewsResponse
import timber.log.Timber
import java.lang.Exception

const val NEWS_PAGE_SIZE = 20

abstract class BaseNewsDataSource: PagingSource<Int, NewsModel>() {

    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
        return try {
            val nextPageNumber = params.key ?: 0
            Timber.d("Next number = $nextPageNumber")
            val response = fetchData(nextPageNumber, NEWS_PAGE_SIZE)
            NewsModel.count = response.totalArticles
            LoadResult.Page(
                data = response.map(),
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.totalArticles) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    abstract suspend fun fetchData(nextPageNumber: Int, size: Int): NewsResponse

}