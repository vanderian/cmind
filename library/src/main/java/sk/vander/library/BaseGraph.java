package sk.vander.library;


import sk.vander.library.navigation.activity.ActivityScreenSwitcher;

/**
 * Created by arashid on 24/06/16.
 */
public interface BaseGraph {
  AppContainer appContainer();
  ActivityHierarchyServer activityHierarchyServer();
  ActivityScreenSwitcher activityScreenSwitcher();
}
