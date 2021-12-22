package com.corbstech.guardian.articles.list.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corbstech.guardian.R
import com.corbstech.guardian.articles.data.model.Article
import com.corbstech.guardian.common.list.AdapterListener
import com.corbstech.guardian.common.list.ItemView
import com.corbstech.guardian.common.list.RecyclerItem
import com.corbstech.guardian.databinding.ListItemArticleBinding

object ArticleItemView : ItemView<RecyclerItem>() {

  override fun belongsTo(item: RecyclerItem?) = item is Article
  override fun type() = R.layout.list_item_article

  override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
    return ArticleItemViewHolder(
      ListItemArticleBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bind(
    holder: RecyclerView.ViewHolder,
    item: RecyclerItem?,
    listener: AdapterListener?
  ) {
    if (holder is ArticleItemViewHolder && item is Article) {
      holder.bind(item)
      holder.itemView.setOnClickListener {
        listener?.clickListener(item)
      }
    }
  }
}
