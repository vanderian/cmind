package sk.vander.cmind.data.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import sk.vander.library.annotation.ApplicationScope;
import sk.vander.library.prefs.BooleanPreference;

/**
 * Created by arashid on 27/06/16.
 */
@Module(includes = ApiModule.class)
public class DebugApiModule {

  @Provides @ApplicationScope @MockMode Boolean provideMockMode(@MockMode BooleanPreference mockPref) {
    return mockPref.get();
  }

  @Provides @ApplicationScope HttpLoggingInterceptor.Level provideLoggingLevel() {
    return HttpLoggingInterceptor.Level.BODY;
  }

  @ApplicationScope @Provides HttpLoggingInterceptor provideLoggingInterceptor(HttpLoggingInterceptor.Level level) {
    final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(level);
    return interceptor;
  }

  @ApplicationScope @Provides OkHttpClient provideOkHttpClient(Cache cache,
                                                               HttpLoggingInterceptor loggingInterceptor,
                                                               @ForceCacheInterceptor Interceptor forceCache) {
    return new OkHttpClient.Builder()
        .addNetworkInterceptor(new StethoInterceptor())
//        .addNetworkInterceptor(forceCache)
        .addInterceptor(loggingInterceptor)
        .cache(cache)
        .build();
  }

  @ApplicationScope @Provides NetworkBehavior provideNetworkBehavior() {
    return NetworkBehavior.create();
  }

  @ApplicationScope @Provides MockRetrofit provideMockRetrofit(Retrofit retrofit, NetworkBehavior networkBehavior) {
    return new MockRetrofit.Builder(retrofit).networkBehavior(networkBehavior).build();
  }
}
