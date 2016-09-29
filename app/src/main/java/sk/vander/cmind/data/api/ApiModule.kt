package sk.vander.cmind.data.api

import auto.parcelgson.gson.AutoParcelGsonTypeAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sk.vander.library.annotation.ApplicationScope
import sk.vander.library.retrofit.error.RxErrorHandlingCallAdapterFactory

/**
 * Created by arashid on 27/06/16.
 */
@Module
class ApiModule {

  @ApplicationScope @Provides fun provideGson(): Gson {
    return GsonBuilder().registerTypeAdapterFactory(AutoParcelGsonTypeAdapterFactory()).create()
  }

  @ApplicationScope @Provides fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(HOST + API).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())//        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()
  }

  @ApplicationScope @Provides @ForceCacheInterceptor fun provideForceCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
      val originalResponse = chain.proceed(chain.request())
      val cacheControl = originalResponse.header("Cache-Control")

      if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
          cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
        originalResponse.newBuilder().removeHeader("pragma").header("Cache-Control", "public, max-age=" + 10).build()
      } else {
        originalResponse
      }
    }
  }

  companion object {
    val HOST = "https://host.com/"
    private val API = "api/"
  }
}