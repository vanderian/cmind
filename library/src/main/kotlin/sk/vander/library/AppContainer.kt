package sk.vander.library

import android.app.Activity
import android.view.ViewGroup

/**
 * An indirection which allows controlling the root container used for each activity.
 */
interface AppContainer {
  /**
   * The root [ViewGroup] into which the activity should place its contents.
   */
  operator fun get(activity: Activity): ViewGroup

  companion object {

    /**
     * An [AppContainer] which returns the normal activity content view.
     */
    val DEFAULT = object : AppContainer {
      override fun get(activity: Activity): ViewGroup {
        return activity.findViewById(android.R.id.content) as ViewGroup
      }
    }
  }
}
