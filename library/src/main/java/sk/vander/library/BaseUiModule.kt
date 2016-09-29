package sk.vander.library

import android.app.Activity

import dagger.Module
import dagger.Provides
import sk.vander.library.annotation.ActivityScreenSwitcherServer
import sk.vander.library.annotation.ApplicationScope
import sk.vander.library.navigation.activity.ActivityScreenSwitcher

/**
 * Created by arashid on 21/06/16.
 */
@Module
class BaseUiModule {

  @Provides @ApplicationScope fun provideActivityScreenSwitcher(): ActivityScreenSwitcher {
    return ActivityScreenSwitcher()
  }

  @Provides
  @ApplicationScope
  @ActivityScreenSwitcherServer
  fun provideActivityScreenSwitcherServer(screenSwitcher: ActivityScreenSwitcher): ActivityHierarchyServer {
    return object : ActivityHierarchyServer.Empty() {
      override fun onActivityStarted(activity: Activity) {
        screenSwitcher.attach(activity)
      }

      override fun onActivityStopped(activity: Activity) {
        screenSwitcher.detach()
      }
    }
  }
}