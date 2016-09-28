package sk.vander.cmind.data;

import dagger.Module;
import sk.vander.cmind.data.api.ReleaseApiModule;

/**
 * Created by arashid on 27/06/16.
 */
@Module(includes = {DataModule.class, ReleaseApiModule.class})
public class ReleaseDataModule {
}
