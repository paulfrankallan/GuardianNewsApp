package com.corbstech.guardian.articles.data

import com.corbstech.guardian.articles.api.ApiArticleDetailResponse
import com.corbstech.guardian.articles.api.ApiArticleListResponse
import com.corbstech.guardian.articles.data.model.Article

class ArticleMapper {

    fun mapArticles(apiArticleListResponse: ApiArticleListResponse): List<Article> {
        return apiArticleListResponse.response.results.map {
            with(it) {
                Article(
                    id = id,
                    thumbnail = fields?.thumbnail ?: "",
                    published = webPublicationDate,
                    title = fields?.headline ?: "",
                    url = apiUrl
                )
            }
        }
    }

    fun mapArticle(apiArticleDetailResponse: ApiArticleDetailResponse) =
        with(apiArticleDetailResponse.response.content) {
            Article(
                id = id,
                thumbnail = fields?.thumbnail ?: "",
                published = webPublicationDate,
                title = fields?.headline ?: "",
                body = fields?.body ?: "",
                url = apiUrl
            )
        }
}
