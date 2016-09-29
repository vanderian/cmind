package sk.vander.cmind.ui

import android.webkit.WebView
import android.widget.ImageView
import autodagger.AutoInjector
import butterknife.bindView
import com.jakewharton.rxbinding.view.clicks
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

  val webView by bindView<WebView>(R.id.web_view)
  val imageView by bindView<ImageView>(R.id.image_view)

  override fun onInject() {
    DaggerService.getDaggerComponent<ContentActivityComponent>(context).inject(this)
  }

  override fun onResume() {
    super.onResume()

    webView.loadUrl("google.com")

    subscription.add(
        imageView.clicks().subscribe {
          picasso.load("https://unsplash.it/200/300/?random")
              .into(imageView)
        }
    )
  }
}