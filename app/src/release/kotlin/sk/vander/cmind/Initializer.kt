package sk.vander.cmind

import sk.vander.library.BaseAppModule

/**
 * Created by arashid on 26/06/16.
 */
object Initializer {
  internal fun init(app: App): AppComponent {
    return DaggerAppComponent.builder().baseAppModule(BaseAppModule(app)).build()
  }
}// No instances.
