package sk.vander.library

import android.app.Application
import android.content.Context

import javax.inject.Inject

/**
 * Created by arashid on 21/06/16.
 */
abstract class BaseApp<Component : BaseGraph> : Application() {
  protected lateinit var component: Component
  @Inject lateinit var activityHierarchyServer: ActivityHierarchyServer

  override fun onCreate() {
    super.onCreate()

    buildComponentAndInject()

    registerActivityLifecycleCallbacks(activityHierarchyServer)

    init()
  }

  override fun getSystemService(name: String): Any {
    return if (DaggerService.SERVICE_NAME == name) component else super.getSystemService(name)
  }

  protected abstract fun buildComponentAndInject()
  protected abstract fun init()

  companion object {

    operator fun get(context: Context): BaseApp<*> {
      return context.applicationContext as BaseApp<*>
    }
  }
}
