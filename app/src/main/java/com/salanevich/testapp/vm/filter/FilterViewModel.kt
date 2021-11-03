package com.salanevich.testapp.vm.filter

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salanevich.testapp.R
import com.salanevich.testapp.cache.filter.Filter
import com.salanevich.testapp.cache.filter.Filter.Companion.toSearchIn
import com.salanevich.testapp.cache.filter.FilterCache
import java.lang.Exception
import java.util.*

class FilterViewModel(private val cache: FilterCache): ViewModel() {

    private val _fromDateLiveData = MutableLiveData<Long?>()
    val fromDate: LiveData<Long?> = _fromDateLiveData
    private val _toDateLiveData = MutableLiveData<Long?>()
    val toDate: LiveData<Long?> = _toDateLiveData
    private val _searchInLiveData = MutableLiveData<List<Filter.SearchIn>>()
    val searchIn: LiveData<List<Filter.SearchIn>> = _searchInLiveData

    fun initDates() {
        _fromDateLiveData.postValue(cache.getDateFrom())
        _toDateLiveData.postValue(cache.getDateTo())
    }

    fun initSearchIn() {
        _searchInLiveData.postValue(cache.getSearchInItems())
    }

    @Throws(Exception::class)
    fun applyFilter(from: String, to: String, searchIn: String, context: Context) {
        cache.setDateFrom(fetchDate(from))
        cache.setDateTo(fetchDate(to))
        val none = context.getString(R.string.none)
        val all = context.getString(R.string.all)
        val array = searchIn.split(",")
        array.forEachIndexed { index, value ->
            if (index == 0) {
                when (value) {
                    none -> {
                        cache.clearSearchInItems()
                    }
                    all -> {
                        cache.addAllSearchInItems()
                    }
                    else -> {
                        cache.addSearchInItem(value.toSearchIn(context))
                    }
                }
            } else cache.addSearchInItem(value.toSearchIn(context))
        }
    }

    private fun fetchDate(value: String): Long? {
        return if (value.isEmpty()) {
            null
        } else {
            val array = value.split("/")
            val year = array[0].toInt()
            val month = array[1].toInt()
            val day = array[2].toInt()
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            calendar.time.time
        }
    }

}