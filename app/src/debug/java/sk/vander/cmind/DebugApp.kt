package sk.vander.cmind

import autodagger.AutoInjector
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by arashid on 26/06/16.
 */
@AutoInjector(App::class)
class DebugApp : App() {
  override fun buildComponentAndInject() {
    super.buildComponentAndInject()
    component.inject(this)
  }

  override fun init() {
    super.init()
    Timber.plant(Timber.DebugTree())
    LeakCanary.install(this)
    Stetho.initializeWithDefaults(this)
  }
}
