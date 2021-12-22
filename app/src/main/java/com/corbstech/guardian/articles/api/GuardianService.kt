package com.corbstech.guardian.articles.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GuardianService {

    @GET("search?show-fields=headline,thumbnail&page-size=50")
    fun searchArticles(
        @Query("q") searchTerm: String
    ): Single<ApiArticleListResponse>

    @GET
    fun getArticle(
        @Url articleUrl: String,
        @Query("show-fields") fields: String
    ): Single<ApiArticleDetailResponse>
}
