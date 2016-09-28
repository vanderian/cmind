package sk.vander.cmind;

import autodagger.AutoComponent;
import autodagger.AutoInjector;
import sk.vander.cmind.data.ReleaseDataModule;
import sk.vander.cmind.ui.ReleaseUiModule;
import sk.vander.library.annotation.ApplicationScope;

/**
 * Created by arashid on 26/06/16.
 */
@ApplicationScope
@AutoComponent(
    modules = {AppModule.class, ReleaseUiModule.class, ReleaseDataModule.class},
    superinterfaces = AppGraph.class
)
@AutoInjector
public @interface BuildTypeAppComponent {
}
