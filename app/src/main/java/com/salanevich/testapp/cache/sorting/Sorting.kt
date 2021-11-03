package com.salanevich.testapp.cache.sorting

data class Sorting(var parameter: Parameter = Parameter.UPLOAD_DATE) {
    enum class Parameter(val value: String) {
        UPLOAD_DATE("publishedAt"),
        RELEVANCE("relevance")
    }
}