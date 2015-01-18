package sk.dejavu.blog.examples.intercepting.intercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.glassfish.jersey.server.ClientBinding;

import sk.dejavu.blog.examples.intercepting.providers.StringProvider;

/**
 * Make sure the injected client doesn't inherit providers from server.
 * Register {@code IntercetionBinder} and {@code StringProvider}.
 *
 * @author Michal Gajdos
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@ClientBinding(
        inheritServerProviders = false,
        configClass = ClientConfig.Config.class
)
public @interface ClientConfig {

    public static class Config extends org.glassfish.jersey.client.ClientConfig {

        public Config() {
            register(StringProvider.class);
            register(new MyInterceptionBinder());
        }
    }
}
