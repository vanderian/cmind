package sk.vander.library

import android.app.Activity
import android.content.Context

import dagger.Provides
import sk.vander.library.annotation.ActivityScope

/**
 * Created by arashid on 26/06/16.
 */
@dagger.Module
@ActivityScope
class ActivityModule(private val activity: Activity) {

  @Provides @ActivityScope fun provideContext(): Context {
    return activity
  }
}
