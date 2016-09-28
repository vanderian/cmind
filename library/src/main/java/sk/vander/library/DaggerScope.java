package sk.vander.library;

import javax.inject.Scope;

@Scope
public @interface DaggerScope {
    Class<?> value();
}
