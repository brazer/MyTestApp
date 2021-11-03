package com.salanevich.testapp.network.datasource

import android.text.TextUtils
import com.salanevich.testapp.BuildConfig
import com.salanevich.testapp.cache.filter.Filter
import com.salanevich.testapp.cache.sorting.Sorting
import com.salanevich.testapp.network.NewsApi
import com.salanevich.testapp.network.response.NewsResponse
import com.salanevich.testapp.utils.dateTimeFormatter
import java.util.*

class SearchDataSource(
    private val api: NewsApi
): BaseNewsDataSource() {

    lateinit var query: String
    lateinit var filter: Filter
    lateinit var sorting: Sorting.Parameter

    override suspend fun fetchData(nextPageNumber: Int, size: Int): NewsResponse {
        val from = if (filter.dateFrom != null) Date(filter.dateFrom!!) else null
        val to = if (filter.dateTo != null) Date(filter.dateTo!!) else null
        val searchIn = if (filter.searchIn.isNotEmpty()) {
            TextUtils.join(",", filter.searchIn)
        } else null
        return api.search(
            query,
            size,
            nextPageNumber,
            false,
            Locale.getDefault().language,
            searchIn,
            from?.let { dateTimeFormatter.format(it) },
            to?.let { dateTimeFormatter.format(it) },
            sorting.value,
            BuildConfig.TOKEN
        )
    }

}