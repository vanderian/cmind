package sk.vander.cmind.data

import android.content.SharedPreferences

import dagger.Module
import dagger.Provides
import sk.vander.cmind.data.api.DebugApiModule
import sk.vander.cmind.data.api.MockMode
import sk.vander.library.annotation.ApplicationScope
import sk.vander.library.prefs.BooleanPreference

/**
 * Created by arashid on 27/06/16.
 */
@Module(includes = arrayOf(DataModule::class, DebugApiModule::class))
class DebugDataModule {

  @Provides @ApplicationScope @MockMode fun provideMockPreference(sp: SharedPreferences): BooleanPreference {
    return BooleanPreference(sp, "pref_key_mock_mode", true)
  }
}
