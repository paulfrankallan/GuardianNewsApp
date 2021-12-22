package com.corbstech.guardian.articles.list.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corbstech.guardian.R
import com.corbstech.guardian.articles.data.model.Section
import com.corbstech.guardian.common.list.AdapterListener
import com.corbstech.guardian.common.list.ItemView
import com.corbstech.guardian.common.list.RecyclerItem
import com.corbstech.guardian.databinding.ListItemSectionBinding

object SectionItemView : ItemView<RecyclerItem>() {

  override fun belongsTo(item: RecyclerItem?) = item is Section
  override fun type() = R.layout.list_item_section

  override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
    return SectionItemViewHolder(
      ListItemSectionBinding.inflate(
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
    if (holder is SectionItemViewHolder && item is Section) {
      holder.listener = listener
      holder.bind(item)
    }
  }
}
