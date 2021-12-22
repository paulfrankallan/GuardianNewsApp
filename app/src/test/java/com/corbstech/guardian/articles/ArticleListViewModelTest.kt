package com.corbstech.guardian.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.corbstech.guardian.articles.data.ArticleRepository
import com.corbstech.guardian.articles.data.model.Article
import com.corbstech.guardian.articles.data.model.Section
import com.corbstech.guardian.articles.list.ArticleListViewModel
import com.corbstech.guardian.articles.list.ArticleListViewModel.Companion.SECTION_FAVOURITES
import com.corbstech.guardian.articles.list.ArticleListViewModel.Companion.SECTION_LAST_WEEK
import com.corbstech.guardian.articles.list.ArticleListViewModel.Companion.SECTION_OLDER
import com.corbstech.guardian.articles.list.ArticleListViewModel.Companion.SECTION_THIS_WEEK
import com.corbstech.guardian.articles.list.ArticlesState
import com.corbstech.guardian.util.RxSchedulerRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.*

class ArticleListViewModelTest {

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repository = mock<ArticleRepository>()
    private val viewModel = ArticleListViewModel(repository)

    @Test
    fun `No articles should produce init state`() {
        whenever(repository.latestFintechArticles()).thenReturn(Single.just(emptyList()))

        viewModel.syncData()

        Assert.assertEquals(ArticlesState(), viewModel.state.value)
    }

    @Test
    fun `Article from this week should show in This Week section`() {

        val articles = listOf(
            buildArticle()
        )

        val expectedState = buildState(
            sections = listOf(SECTION_THIS_WEEK), refreshing = false, articles = articles
        )

        whenever(repository.latestFintechArticles()).thenReturn(Single.just(articles))

        viewModel.syncData()

        Assert.assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `Favourite article from this week should show in favourites section`() {

        val articles = listOf(
            buildArticle()
        )

        val expectedState = buildState(
            sections = listOf(SECTION_FAVOURITES),
            refreshing = false,
            articles = articles
        )

        whenever(repository.latestFintechArticles()).thenReturn(Single.just(articles))
        whenever(repository.getFavourites()).thenReturn(setOf("1"))

        viewModel.syncData()

        Assert.assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `Article from last week should show in Last Week section`() {

        val articles = listOf(
            buildArticle(
               published = with(Calendar.getInstance()) {
                   time = Date()
                   add(Calendar.DATE, -8) // 8 days ago.
                   time
               }
            )
        )

        val expectedState = buildState(
            sections = listOf(SECTION_LAST_WEEK),
            refreshing = false,
            articles = articles
        )

        whenever(repository.latestFintechArticles()).thenReturn(Single.just(articles))

        viewModel.syncData()

        Assert.assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `Favourite article from last week should show in favourites section`() {

        val articles = listOf(
            buildArticle(
                published = with(Calendar.getInstance()) {
                    time = Date()
                    add(Calendar.DATE, -8) // 8 days ago.
                    time
                }
            )
        )

        val expectedState = buildState(
            sections = listOf(SECTION_FAVOURITES),
            refreshing = false,
            articles = articles
        )

        whenever(repository.latestFintechArticles()).thenReturn(Single.just(articles))
        whenever(repository.getFavourites()).thenReturn(setOf("1"))

        viewModel.syncData()

        Assert.assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `Article from older than last week should show in Older section`() {

        val articles = listOf(
            buildArticle(
                published = with(Calendar.getInstance()) {
                    time = Date()
                    add(Calendar.DATE, -15) // 15 days ago.
                    time
                }
            )
        )

        val expectedState = buildState(
            sections = listOf(SECTION_OLDER),
            refreshing = false,
            articles = articles
        )

        whenever(repository.latestFintechArticles()).thenReturn(Single.just(articles))

        viewModel.syncData()

        Assert.assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `Favourite article from older than last week should show in favourites section`() {

        val articles = listOf(
            buildArticle(
                published = with(Calendar.getInstance()) {
                    time = Date()
                    add(Calendar.DATE, -15) // 15 days ago.
                    time
                }
            )
        )

        val expectedState = buildState(
            sections = listOf(SECTION_FAVOURITES),
            refreshing = false,
            articles = articles
        )

        whenever(repository.latestFintechArticles()).thenReturn(Single.just(articles))
        whenever(repository.getFavourites()).thenReturn(setOf("1"))

        viewModel.syncData()

        Assert.assertEquals(expectedState, viewModel.state.value)
    }

    // region Helpers

    private fun buildArticle(
        id: String = "1",
        title: String = "testTitle",
        body: String = "testBody",
        thumbnail: String = "testThumbnail",
        url: String = "testUrl",
        published: Date = Date() // Today
    ) = Article(
        id = id,
        title = title,
        body = body,
        thumbnail = thumbnail,
        url = url,
        published = published,
    )

    private fun buildState(
        sections: List<String>,
        // Not testing for now...
        @Suppress("SameParameterValue") refreshing: Boolean = false,
        articles: List<Article>
    ) = ArticlesState(
        refreshing = refreshing,
        sections = sections.map { Section(
            id = it,
            title = it,
            articles = articles
        ) }
    )

    // endregion
}
