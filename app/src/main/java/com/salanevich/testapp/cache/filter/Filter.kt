package com.salanevich.testapp.cache.filter

import android.content.Context
import androidx.annotation.StringRes
import com.salanevich.testapp.R
import java.lang.IllegalArgumentException
import java.util.HashSet

data class Filter(
    var dateFrom: Long? = null,
    var dateTo: Long? = null,
    var searchIn: HashSet<SearchIn> = HashSet(3)
) {
    enum class SearchIn(val value: String, @StringRes val id: Int) {
        TITLE("title", R.string.title),
        DESC("description", R.string.desc),
        CONTENT("content", R.string.content)
    }
    companion object {
        fun String.toSearchIn(context: Context): SearchIn {
            return when (this) {
                context.getString(SearchIn.TITLE.id) -> SearchIn.TITLE
                context.getString(SearchIn.DESC.id) -> SearchIn.DESC
                context.getString(SearchIn.CONTENT.id) -> SearchIn.CONTENT
                else -> throw IllegalArgumentException("Unknown item of SearchIn: $this")
            }
        }
    }
}