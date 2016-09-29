package sk.vander.cmind.data.api

import com.facebook.stetho.okhttp3.StethoInterceptor

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import sk.vander.library.annotation.ApplicationScope
import sk.vander.library.prefs.BooleanPreference

/**
 * Created by arashid on 27/06/16.
 */
@Module(includes = arrayOf(ApiModule::class))
class DebugApiModule {

  @Provides @ApplicationScope @MockMode fun provideMockMode(@MockMode mockPref: BooleanPreference): Boolean? {
    return mockPref.get()
  }

  @Provides @ApplicationScope fun provideLoggingLevel(): HttpLoggingInterceptor.Level {
    return HttpLoggingInterceptor.Level.BODY
  }

  @ApplicationScope @Provides fun provideLoggingInterceptor(level: HttpLoggingInterceptor.Level): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = level
    return interceptor
  }

  @ApplicationScope @Provides fun provideOkHttpClient(cache: Cache,
                                                               loggingInterceptor: HttpLoggingInterceptor,
                                                               @ForceCacheInterceptor forceCache: Interceptor): OkHttpClient {
    return OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).addInterceptor(loggingInterceptor)//        .addNetworkInterceptor(forceCache)
        .cache(cache).build()
  }

  @ApplicationScope @Provides fun provideNetworkBehavior(): NetworkBehavior {
    return NetworkBehavior.create()
  }

  @ApplicationScope @Provides fun provideMockRetrofit(retrofit: Retrofit, networkBehavior: NetworkBehavior): MockRetrofit {
    return MockRetrofit.Builder(retrofit).networkBehavior(networkBehavior).build()
  }
}
