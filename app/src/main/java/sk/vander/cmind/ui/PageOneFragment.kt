package sk.vander.cmind.ui

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import autodagger.AutoInjector
import butterknife.bindView
import com.jakewharton.rxbinding.view.clicks
import com.squareup.picasso.Callback
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
  val imageView by bindView<ImageView>(R.id.image)

  init {
    webClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        val next = Uri.parse(url).host.contains("yahoo.com")
        if (next) ((activity as ContentActivity).animator.showNext())
        return next
      }
    }
  }

  override fun onInject() {
    DaggerService.getDaggerComponent<ContentActivityComponent>(context).inject(this)
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    savedInstanceState?.apply {
      // should load from cache
      picasso.load(randomImage).into(imageView)
    }
  }

  override fun onResume() {
    super.onResume()

    webView.setWebViewClient(webClient)
    webView.loadUrl("https://www.google.com")

    subscription.add(
        imageView.clicks().subscribe {
          picasso.cancelRequest(imageView)
          picasso.invalidate(randomImage)
          picasso.load(randomImage).into(imageView, object : Callback {
            override fun onSuccess() {
            }

            override fun onError() {
              Toast.makeText(activity, "error loading image from $randomImage", Toast.LENGTH_SHORT).show()
            }
          })
        }
    )
  }
}