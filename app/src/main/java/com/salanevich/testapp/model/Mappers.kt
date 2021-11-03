package com.salanevich.testapp.model

import com.salanevich.testapp.network.response.NewsResponse

fun NewsResponse.map(): List<NewsModel> {
    val list = mutableListOf<NewsModel>()
    articles?.forEach { item ->
        item?.let {
            list.add(NewsModel(
                urlPicture = it.image,
                title = it.title,
                details = it.description,
                url = it.url
            ))
        }
    }
    return list
}