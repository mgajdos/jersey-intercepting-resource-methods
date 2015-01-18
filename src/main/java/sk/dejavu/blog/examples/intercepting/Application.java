package sk.dejavu.blog.examples.intercepting;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import sk.dejavu.blog.examples.intercepting.intercept.MyInterceptionBinder;

/**
 * @author Michal Gajdos
 */
@ApplicationPath("/")
public class Application extends ResourceConfig {

    public Application() {
        packages("sk.dejavu.blog.examples.intercepting");

        // Register Interception Service.
        // Comment if you want to register HK2 services via hk2-inhabitant-generator (don't forget to enable it in pom.xml).
        register(new MyInterceptionBinder());
    }

}
