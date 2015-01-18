package sk.dejavu.blog.examples.intercepting.intercept;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Register our custom {@code InterceptionService} into HK2.
 *
 * @author Michal Gajdos
 */
public class MyInterceptionBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(MyInterceptionService.class)
                .to(org.glassfish.hk2.api.InterceptionService.class)
                .in(Singleton.class);
    }
}
