package sk.vander.cmind


import com.squareup.picasso.Picasso

import sk.vander.library.BaseGraph

/**
 * Created by arashid on 26/06/16.
 */
interface AppGraph : BaseGraph {
  fun picasso(): Picasso
}
