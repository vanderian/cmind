package sk.vander.cmind.ui.adapter

import sk.vander.cmind.R
import sk.vander.library.adapter.ListSource

/**
 * Author vander @ 10/5/16.
 */
class NumberSource() : ListSource<String>() {
  override fun getViewType(pos: Int): Int = 0

  override fun getLayoutRes(viewType: Int): Int = R.layout.view_item_number

  override fun getItemId(position: Int): Long = position.toLong()
}