package com.corbstech.guardian.articles.list

import com.corbstech.guardian.articles.data.ArticleRepository
import com.corbstech.guardian.articles.data.model.Article
import com.corbstech.guardian.articles.data.model.Section
import com.corbstech.guardian.common.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(private val repository: ArticleRepository) :
    BaseViewModel<ArticlesState>(ArticlesState()) {

    fun syncData() {
        setState { copy(refreshing = true) }
        disposables += repository.latestFintechArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { articles ->
                    setState { copy(refreshing = false, sections = mapSections(articles)) }
                },
                {
                    setState { ArticlesState() }
                }
            )
    }

    private fun mapSections(articles: List<Article>): List<Section> {
        return mutableListOf<Section>().apply {
            val favourites = repository.getFavourites()
            applySection(this, articles, SECTION_FAVOURITES) {
                articles.filter {
                    favourites.contains(it.id)
                }.sortedByDescending {
                    it.published
                }
            }
            applySection(this, articles, SECTION_THIS_WEEK) {
                articles.filter {
                    Instant.ofEpochMilli(it.published.time)
                        .atZone(ZoneId.systemDefault()).toLocalDate()
                        .isThisWeek() && !favourites.contains(it.id)
                }.sortedByDescending {
                    it.published
                }
            }
            applySection(this, articles, SECTION_LAST_WEEK) {
                articles.filter {
                    Instant.ofEpochMilli(it.published.time)
                        .atZone(ZoneId.systemDefault()).toLocalDate()
                        .isLastWeek() && !favourites.contains(it.id)
                }.sortedByDescending {
                    it.published
                }
            }
            applySection(this, articles, SECTION_OLDER) {
                articles.filter {
                    Instant.ofEpochMilli(it.published.time)
                        .atZone(ZoneId.systemDefault()).toLocalDate()
                        .isBeforeLastWeek() && !favourites.contains(it.id)
                }.sortedByDescending {
                    it.published
                }
            }
        }
    }

    private fun applySection(
        sections: MutableList<Section>,
        articles: List<Article>,
        title: String,
        filter: (List<Article>) -> List<Article>
    ) {
        filter(articles).apply {
            if (isNotEmpty()) {
                sections.add(
                    Section(
                        id = title,
                        title = title,
                        articles = this
                    )
                )
            }
        }
    }

    companion object {
        const val SECTION_FAVOURITES = "Favourites"
        const val SECTION_THIS_WEEK = "This Week"
        const val SECTION_LAST_WEEK = "Last Week"
        const val SECTION_OLDER = "Older"
    }
}

data class ArticlesState(
    val refreshing: Boolean = false,
    val sections: List<Section> = emptyList()
)
