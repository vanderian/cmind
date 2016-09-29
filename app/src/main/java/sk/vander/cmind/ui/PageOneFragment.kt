package sk.vander.cmind.ui

import android.webkit.WebView
import android.webkit.WebViewClient
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
  val webClient: WebViewClient

  val randomImage = "https://unsplash.it/800/?random"
  val webView by bindView<WebView>(R.id.web_view)
  val imageView by bindView<ImageView>(R.id.image_view)

  init {
    webClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        val next = url.contains("yahoo.com")
        return next
      }
    }
  }

  override fun onInject() {
    DaggerService.getDaggerComponent<ContentActivityComponent>(context).inject(this)
  }

  override fun onResume() {
    super.onResume()

    webView.setWebViewClient(webClient)
    webView.loadUrl("https://www.google.com")

    subscription.add(
        imageView.clicks().subscribe {
          picasso.invalidate(randomImage)
          picasso.load(randomImage).into(imageView)
        }
    )
  }
}