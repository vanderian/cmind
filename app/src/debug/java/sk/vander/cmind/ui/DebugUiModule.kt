package sk.vander.cmind.ui

import android.content.Context
import dagger.Module
import dagger.Provides
import sk.vander.cmind.ui.debug.DebugAppContainer
import sk.vander.library.ActivityHierarchyServer
import sk.vander.library.AppContainer
import sk.vander.library.ViewServer
import sk.vander.library.annotation.ActivityScreenSwitcherServer
import sk.vander.library.annotation.ApplicationScope

/**
 * Created by arashid on 26/06/16.
 */
@Module(includes = arrayOf(UiModule::class))
class DebugUiModule {

  @Provides @ApplicationScope fun providesAppContainer(container: DebugAppContainer): AppContainer {
    return container
  }

  @Provides
  @ApplicationScope
  fun provideActivityHierarchyServer(ctx: Context, @ActivityScreenSwitcherServer server: ActivityHierarchyServer): ActivityHierarchyServer {
    val proxy = ActivityHierarchyServer.Proxy()
    proxy.addServer(server)
    proxy.addServer(ViewServer.create(ctx))
    return proxy
  }
}
