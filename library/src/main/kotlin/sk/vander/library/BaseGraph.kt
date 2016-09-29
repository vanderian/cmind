package sk.vander.library


import sk.vander.library.navigation.activity.ActivityScreenSwitcher

/**
 * Created by arashid on 24/06/16.
 */
interface BaseGraph {
  fun appContainer(): AppContainer
  fun activityHierarchyServer(): ActivityHierarchyServer
  fun activityScreenSwitcher(): ActivityScreenSwitcher
}
