package com.corbstech.guardian.articles.data

import android.content.SharedPreferences
import com.corbstech.guardian.articles.api.GuardianService
import javax.inject.Inject

open class ArticleRepository @Inject constructor(
    private var preferences: SharedPreferences,
    private var guardianService: GuardianService,
    private var articleMapper: ArticleMapper
) {
    fun latestFintechArticles() =
        guardianService.searchArticles(SEARCH_QUERY)
            .map { articleMapper.mapArticles(it) }

    fun getArticle(articleUrl: String) =
        guardianService.getArticle(articleUrl, SEARCH_FIELDS)
            .map { articleMapper.mapArticle(it) }

    fun getFavourites(): Set<String> =
        preferences.getStringSet(PREFERENCE_FAVOURITES_KEY, mutableSetOf<String>()) ?: setOf()

    fun saveFavourites(favourites: Set<String>) =
        preferences.edit().putStringSet(PREFERENCE_FAVOURITES_KEY, favourites).apply()

    companion object {
        private const val SEARCH_QUERY = "fintech,brexit"
        private const val SEARCH_FIELDS = "main,body,headline,thumbnail"
        private const val PREFERENCE_FAVOURITES_KEY = "prefs_key_favourites"
    }
}
