package sk.vander.cmind.data

import dagger.Module
import sk.vander.cmind.data.api.ReleaseApiModule

/**
 * Created by arashid on 27/06/16.
 */
@Module(includes = arrayOf(DataModule::class, ReleaseApiModule::class))
class ReleaseDataModule
