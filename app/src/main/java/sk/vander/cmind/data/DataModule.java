package sk.vander.cmind.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import sk.vander.cmind.App;
import sk.vander.cmind.data.api.ApiModule;
import sk.vander.library.annotation.ApplicationScope;
import sk.vander.library.prefs.RxSharedPreferences;

/**
 * Created by arashid on 27/06/16.
 */
@Module(includes = ApiModule.class)
public class DataModule {
  private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

  @Provides @ApplicationScope RxSharedPreferences provideRxPreferences(App app, SharedPreferences sp) {
    return RxSharedPreferences.create(app.getResources(), sp);
  }

  @Provides @ApplicationScope Cache provideCache(@ApplicationScope Context context) {
    final File cacheDir = new File(context.getCacheDir(), "okhttp3-cache");
    return new Cache(cacheDir, CACHE_SIZE);
  }
}
