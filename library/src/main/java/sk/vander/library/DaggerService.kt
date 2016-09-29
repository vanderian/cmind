package sk.vander.library

import android.content.Context

object DaggerService {

  val SERVICE_NAME = DaggerService::class.java.name

  /**
   * Caller is required to know the type of the component for this context.
   */
  @SuppressWarnings("unchecked") //
  fun <T> getDaggerComponent(context: Context): T {
    return context.getSystemService(SERVICE_NAME) as T
  }
}
