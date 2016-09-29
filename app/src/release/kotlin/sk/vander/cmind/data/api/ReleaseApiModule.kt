package sk.vander.cmind.data.api

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import sk.vander.library.annotation.ApplicationScope

/**
 * Created by arashid on 27/06/16.
 */
@Module(includes = arrayOf(ApiModule::class))
class ReleaseApiModule {
  @Provides @ApplicationScope fun provideOkHttpClient(cache: Cache, @ForceCacheInterceptor forceCache: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
//        .addNetworkInterceptor(forceCache)
        .cache(cache)
        .build()
  }
}
