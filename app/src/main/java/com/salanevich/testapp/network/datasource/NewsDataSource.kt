package com.salanevich.testapp.network.datasource

import com.salanevich.testapp.BuildConfig
import com.salanevich.testapp.network.NewsApi
import com.salanevich.testapp.network.response.NewsResponse
import java.util.*

class NewsDataSource(
    private val api: NewsApi
): BaseNewsDataSource() {
    override suspend fun fetchData(nextPageNumber: Int, size: Int): NewsResponse {
        return api.fetchNews(
            size,
            nextPageNumber,
            false,
            Locale.getDefault().language,
            BuildConfig.TOKEN
        )
    }
}