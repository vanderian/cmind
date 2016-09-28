package sk.vander.cmind.ui;

import dagger.Module;
import dagger.Provides;
import sk.vander.library.ActivityHierarchyServer;
import sk.vander.library.AppContainer;
import sk.vander.library.annotation.ActivityScreenSwitcherServer;
import sk.vander.library.annotation.ApplicationScope;


/**
 * Created by arashid on 26/06/16.
 */
@Module(includes = UiModule.class)
public class ReleaseUiModule {

  @Provides @ApplicationScope AppContainer providesAppContainer() {
    return AppContainer.DEFAULT;
  }

  @Provides @ApplicationScope
  ActivityHierarchyServer provideActivityHierarchyServer(@ActivityScreenSwitcherServer ActivityHierarchyServer server) {
    return server;
  }
}
