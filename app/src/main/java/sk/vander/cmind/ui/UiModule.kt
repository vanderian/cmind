package sk.vander.cmind.ui

import android.content.Context

import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import sk.vander.library.BaseUiModule
import sk.vander.library.annotation.ApplicationScope

/**
 * Created by arashid on 26/06/16.
 */
@Module(includes = arrayOf(BaseUiModule::class))
class UiModule {

  @Provides @ApplicationScope fun providePicasso(@ApplicationScope ctx: Context, okHttpClient: OkHttpClient): Picasso {
    return Picasso.Builder(ctx).downloader(OkHttp3Downloader(okHttpClient)).build()
  }
}
