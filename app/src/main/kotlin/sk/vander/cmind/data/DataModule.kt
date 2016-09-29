package sk.vander.cmind.data

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import sk.vander.cmind.App
import sk.vander.cmind.data.api.ApiModule
import sk.vander.library.annotation.ApplicationScope
import sk.vander.library.prefs.RxSharedPreferences
import java.io.File

/**
 * Created by arashid on 27/06/16.
 */
@Module(includes = arrayOf(ApiModule::class))
class DataModule {

  @Provides @ApplicationScope fun provideRxPreferences(app: App, sp: SharedPreferences): RxSharedPreferences {
    return RxSharedPreferences.create(app.resources, sp)
  }

  @Provides @ApplicationScope fun provideCache(@ApplicationScope context: Context): Cache {
    val cacheDir = File(context.cacheDir, "okhttp3-cache")
    return Cache(cacheDir, CACHE_SIZE)
  }

  companion object {
    private val CACHE_SIZE = 10 * 1024 * 1024.toLong() // 10 MB
  }
}
