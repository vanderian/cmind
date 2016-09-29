package sk.vander.library

import android.app.Application
import android.content.Context

import dagger.Module
import dagger.Provides
import sk.vander.library.annotation.ApplicationScope

/**
 * Created by vander on 4/17/15.
 */
@Module
class BaseAppModule(private val app: Application) {

  @Provides @ApplicationScope fun provideApplication(): Application {
    return app
  }

  @Provides @ApplicationScope fun provideApplicationContext(): Context {
    return app.applicationContext
  }
}
