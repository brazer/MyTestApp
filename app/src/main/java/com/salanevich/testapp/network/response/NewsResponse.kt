package com.salanevich.testapp.network.response

data class NewsResponse(
	val totalArticles: Int,
	val articles: List<ArticlesItem?>? = null
)

data class ArticlesItem(
	val image: String,
	val publishedAt: String? = null,
	val description: String,
	val source: Source? = null,
	val title: String,
	val content: String? = null,
	val url: String
)

data class Source(
	val name: String? = null,
	val url: String? = null
)

