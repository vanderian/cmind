package sk.vander.cmind.ui;

import dagger.Module;
import dagger.Provides;
import sk.vander.cmind.ui.debug.DebugAppContainer;
import sk.vander.library.ActivityHierarchyServer;
import sk.vander.library.AppContainer;
import sk.vander.library.SocketActivityHierarchyServer;
import sk.vander.library.annotation.ActivityScreenSwitcherServer;
import sk.vander.library.annotation.ApplicationScope;

/**
 * Created by arashid on 26/06/16.
 */
@Module(includes = UiModule.class)
public class DebugUiModule {

  @Provides @ApplicationScope AppContainer providesAppContainer(DebugAppContainer container) {
    return container;
  }

  @Provides @ApplicationScope
  ActivityHierarchyServer provideActivityHierarchyServer(@ActivityScreenSwitcherServer ActivityHierarchyServer server) {
    final ActivityHierarchyServer.Proxy proxy = new ActivityHierarchyServer.Proxy();
    proxy.addServer(server);
    proxy.addServer(new SocketActivityHierarchyServer());
    return proxy;
  }
}
