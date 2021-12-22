package com.corbstech.guardian.common.list

interface RecyclerItem {
  val id: String?
  override fun equals(other: Any?): Boolean
}
