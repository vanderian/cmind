package sk.vander.cmind.ui

import android.net.Uri
import autodagger.AutoInjector
import rx.Observable
import rx.subscriptions.CompositeSubscription
import sk.vander.cmind.AppComponent
import sk.vander.cmind.R
import sk.vander.cmind.StandardActivity
import sk.vander.library.BaseActivity
import sk.vander.library.DaggerService
import sk.vander.library.annotation.LayoutId
import sk.vander.library.annotation.ScreenLabel
import sk.vander.library.navigation.activity.ActivityUriScreen
import java.util.concurrent.TimeUnit

@StandardActivity
@AutoInjector
@ScreenLabel(R.string.label_splash)
@LayoutId(R.layout.activity_splash)
class SplashActivity : BaseActivity() {
  val subscription = CompositeSubscription()
//  @Inject lateinit var picasso : Picasso

  override fun onCreateComponent(appComponent: Any): Any {
    return DaggerSplashActivityComponent.builder().appComponent(appComponent as AppComponent).build()
  }

  override fun onInject() {
    DaggerService.getDaggerComponent<DaggerSplashActivityComponent>(this).inject(this)
  }

  override fun onResume() {
    super.onResume()

    assets.list("")[0]

    subscription.add(
        Observable.timer(5, TimeUnit.SECONDS)
            .map { ActivityUriScreen.withUri(Uri.parse(getString(R.string.nav_content))) }
            .subscribe { screenSwitcher.open(it, true) }
    )
  }

  override fun onPause() {
    super.onPause()
    subscription.clear()
  }
}
