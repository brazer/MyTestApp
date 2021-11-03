package com.salanevich.testapp.model

data class NewsModel(
    val urlPicture: String?,
    val title: String,
    val details: String,
    val url: String
) {
    companion object {
        var count = -1
    }
}
