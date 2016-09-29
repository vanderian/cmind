package sk.vander.cmind

import autodagger.AutoComponent
import sk.vander.library.ActivityModule
import sk.vander.library.annotation.ActivityScope

/**
 * Created by arashid on 26/06/16.
 */
@AutoComponent(dependencies = arrayOf(App::class), modules = arrayOf(ActivityModule::class))
@ActivityScope
annotation class StandardActivity
