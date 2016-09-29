package sk.vander.cmind.ui

import autodagger.AutoInjector
import com.squareup.picasso.Picasso
import sk.vander.cmind.R
import sk.vander.library.BaseFragment
import sk.vander.library.DaggerService
import sk.vander.library.annotation.LayoutId
import javax.inject.Inject

/**
 * Author vander @ 9/29/16.
 */
@AutoInjector(ContentActivity::class)
@LayoutId(R.layout.fragment_page_one)
class PageOneFragment : BaseFragment() {
  @Inject lateinit var picasso: Picasso

  override fun onInject() {
    DaggerService.getDaggerComponent<ContentActivityComponent>(context).inject(this)
  }
}