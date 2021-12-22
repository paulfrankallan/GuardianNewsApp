package com.corbstech.guardian.articles.list.item

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corbstech.guardian.articles.data.model.Article
import com.corbstech.guardian.databinding.ListItemArticleBinding
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd/MM/yyyy"

class ArticleItemViewHolder(private val binding: ListItemArticleBinding)
  : RecyclerView.ViewHolder(binding.root) {

  private val dateFormat by lazy {
    SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
  }

  fun bind(article: Article) {
    binding.articleHeadlineTextview.text = article.title
    binding.articleDateTextview.text = dateFormat.format(article.published)
    Glide.with(itemView.context).load(article.thumbnail).into(binding.articleThumbnail)
  }
}