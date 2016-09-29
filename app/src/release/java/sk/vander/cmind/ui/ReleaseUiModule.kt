package sk.vander.cmind.ui

import dagger.Module
import dagger.Provides
import sk.vander.library.ActivityHierarchyServer
import sk.vander.library.AppContainer
import sk.vander.library.annotation.ActivityScreenSwitcherServer
import sk.vander.library.annotation.ApplicationScope


/**
 * Created by arashid on 26/06/16.
 */
@Module(includes = arrayOf(UiModule::class))
class ReleaseUiModule {

  @Provides @ApplicationScope fun providesAppContainer(): AppContainer {
    return AppContainer.DEFAULT
  }

  @Provides
  @ApplicationScope
  fun provideActivityHierarchyServer(@ActivityScreenSwitcherServer server: ActivityHierarchyServer): ActivityHierarchyServer {
    return server
  }
}
