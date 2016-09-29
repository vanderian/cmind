package sk.vander.cmind

import android.support.annotation.CallSuper
import sk.vander.library.BaseApp
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by arashid on 26/06/16.
 */
@BuildTypeAppComponent
open class App : BaseApp<AppComponent>() {
//  @Inject var picasso: Picasso? = null

  override fun buildComponentAndInject() {
    component = Initializer.init(this)
    component.inject(this)
  }

  @CallSuper override fun init() {
    CalligraphyConfig.initDefault(CalligraphyConfig.Builder().build())

//    Picasso.setSingletonInstance(picasso)
  }
}
