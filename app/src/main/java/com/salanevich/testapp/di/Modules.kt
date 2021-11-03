package com.salanevich.testapp.di

import com.salanevich.testapp.cache.filter.FilterCache
import com.salanevich.testapp.cache.filter.FilterCacheImpl
import com.salanevich.testapp.cache.history.SearchHistoryCache
import com.salanevich.testapp.cache.history.SearchHistoryCacheImpl
import com.salanevich.testapp.cache.sorting.SortingCache
import com.salanevich.testapp.cache.sorting.SortingCacheImpl
import com.salanevich.testapp.network.NewsApi
import com.salanevich.testapp.network.datasource.NewsDataSource
import com.salanevich.testapp.network.datasource.SearchDataSource
import com.salanevich.testapp.vm.filter.FilterViewModel
import com.salanevich.testapp.vm.NewsViewModel
import com.salanevich.testapp.vm.HomeViewModel
import com.salanevich.testapp.vm.SearchViewModel
import com.salanevich.testapp.vm.filter.SearchInViewModel
import com.salanevich.testapp.vm.repository.NewsRepository
import com.salanevich.testapp.vm.repository.SearchRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<SortingCache> { SortingCacheImpl() }
    single<FilterCache> { FilterCacheImpl() }
    single<SearchHistoryCache> { SearchHistoryCacheImpl() }
    single { NewsApi.invoke() }
    single { SearchDataSource(get()) }
    single { SearchRepository(get()) }
    single { NewsDataSource(get()) }
    single { NewsRepository(get()) }

    viewModel { NewsViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { SearchViewModel(get(), get(), get(), get()) }
    viewModel { FilterViewModel(get()) }
    viewModel { SearchInViewModel(get()) }

}