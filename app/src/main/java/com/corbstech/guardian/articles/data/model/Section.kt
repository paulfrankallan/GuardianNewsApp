package com.corbstech.guardian.articles.data.model

import com.corbstech.guardian.common.list.RecyclerItem
import com.corbstech.guardian.common.list.RecyclerItemClicked

data class Section(
  override val id: String?,
  val title: String,
  val articles: List<RecyclerItem>
) : RecyclerItem, RecyclerItemClicked
