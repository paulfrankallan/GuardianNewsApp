package com.corbstech.guardian.articles.api

data class ApiArticleListResponse(val response: ApiArticleList)
data class ApiArticleList(val results: List<ApiArticle>)
