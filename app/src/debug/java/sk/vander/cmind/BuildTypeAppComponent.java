package sk.vander.cmind;

import autodagger.AutoComponent;
import autodagger.AutoInjector;
import sk.vander.cmind.data.DebugDataModule;
import sk.vander.cmind.ui.DebugUiModule;
import sk.vander.library.annotation.ApplicationScope;

/**
 * Created by arashid on 26/06/16.
 */
@ApplicationScope
@AutoComponent(
    modules = {DebugAppModule.class, DebugUiModule.class, DebugDataModule.class},
    superinterfaces = DebugAppGraph.class
)
@AutoInjector
public @interface BuildTypeAppComponent {
}
