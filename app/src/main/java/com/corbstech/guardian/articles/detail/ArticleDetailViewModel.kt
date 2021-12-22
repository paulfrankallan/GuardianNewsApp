package com.corbstech.guardian.articles.detail

import com.corbstech.guardian.articles.data.ArticleRepository
import com.corbstech.guardian.articles.data.model.Article
import com.corbstech.guardian.common.BaseViewModel
import com.corbstech.guardian.common.plusAssign
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(private val repository: ArticleRepository) :
    BaseViewModel<ArticleDetailState>(ArticleDetailState()) {

    fun syncData(articleUrl: String) {
        setState { copy(refreshing = true) }
        disposables += repository.getArticle(articleUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { article ->
                    setState {
                        copy(
                            refreshing = false,
                            article = article,
                            favourite = repository.getFavourites().contains(article.id)
                        )
                    }
                },
                {
                    setState { ArticleDetailState() }
                }
            )
    }

    fun onFavouriteToggled() {
        state.value?.article?.id?.let {
            setState {
                copy(
                    favourite = toggleFavourite(it)
                )
            }
        }
    }

    private fun toggleFavourite(id: String) = with(
        repository.getFavourites().toMutableSet()
    ) {
        val isFavourite = contains(id)
        if (isFavourite) {
            remove(id)
        } else {
            add(id)
        }
        repository.saveFavourites(this)
        !isFavourite
    }
}

data class ArticleDetailState(
    val refreshing: Boolean = false,
    val article: Article? = null,
    val favourite: Boolean = false
)
