package com.corbstech.guardian.common.list

import androidx.recyclerview.widget.DiffUtil

val BASE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecyclerItem>() {

  override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
    return oldItem == newItem
  }
}
