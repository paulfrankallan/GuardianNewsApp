package com.corbstech.guardian.articles.list.item

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corbstech.guardian.articles.data.model.Section
import com.corbstech.guardian.articles.list.item.ArticleItemView
import com.corbstech.guardian.common.list.AdapterListener
import com.corbstech.guardian.common.list.ListAdapter
import com.corbstech.guardian.common.list.RecyclerItem
import com.corbstech.guardian.databinding.ListItemSectionBinding

class SectionItemViewHolder(private val binding: ListItemSectionBinding)
  : RecyclerView.ViewHolder(binding.root) {

  var listener: AdapterListener? = null

  fun bind(section: Section) {

    binding.sectionTitleTextview.text = section.title

    val listAdapter = ListAdapter(
      listener = listener,
      itemViewTypes = listOf(
        ArticleItemView
      )
    )

    binding.articlesRecyclerview.apply {
      layoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
      )
      adapter = listAdapter
    }
    listAdapter.submitList(
      mutableListOf<RecyclerItem>().apply {
        addAll(section.articles)
      }
    )
  }
}
