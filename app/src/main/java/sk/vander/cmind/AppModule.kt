package sk.vander.cmind

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import autodagger.AutoExpose
import dagger.Module
import dagger.Provides
import sk.vander.library.BaseAppModule
import sk.vander.library.annotation.ApplicationScope

/**
 * Created by arashid on 26/06/16.
 */
@Module(includes = arrayOf(BaseAppModule::class))
class AppModule {

  @AutoExpose(App::class)
  @Provides
  @ApplicationScope
  fun provideSharedPreferences(application: Application): SharedPreferences {
    return application.getSharedPreferences("name", Context.MODE_PRIVATE)
  }
}
