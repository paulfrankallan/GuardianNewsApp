package com.corbstech.guardian.articles.data.model

import com.corbstech.guardian.common.list.RecyclerItem
import com.corbstech.guardian.common.list.RecyclerItemClicked
import java.util.*

data class Article(
    override val id: String,
    val thumbnail: String,
    val published: Date,
    val title: String,
    val body: String? = null,
    val url: String
): RecyclerItem, RecyclerItemClicked
