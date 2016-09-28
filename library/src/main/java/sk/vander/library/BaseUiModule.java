package sk.vander.library;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import sk.vander.library.annotation.ActivityScreenSwitcherServer;
import sk.vander.library.annotation.ApplicationScope;
import sk.vander.library.navigation.activity.ActivityScreenSwitcher;

/**
 * Created by arashid on 21/06/16.
 */
@Module
public class BaseUiModule {

  @Provides @ApplicationScope ActivityScreenSwitcher provideActivityScreenSwitcher() {
    return new ActivityScreenSwitcher();
  }

  @Provides @ApplicationScope @ActivityScreenSwitcherServer
  ActivityHierarchyServer provideActivityScreenSwitcherServer(final ActivityScreenSwitcher screenSwitcher) {
    return new ActivityHierarchyServer.Empty() {
      @Override
      public void onActivityStarted(Activity activity) {
        screenSwitcher.attach(activity);
      }

      @Override
      public void onActivityStopped(Activity activity) {
        screenSwitcher.detach();
      }
    };
  }
}