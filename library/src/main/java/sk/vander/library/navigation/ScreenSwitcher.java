package sk.vander.library.navigation;

public interface ScreenSwitcher {
  void open(Screen screen);
  void open(Screen screen, boolean finish);
  void goBack();
}
