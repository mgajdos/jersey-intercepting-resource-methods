package sk.dejavu.blog.examples.intercepting.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import sk.dejavu.blog.examples.intercepting.intercept.Intercept;

/**
 * "Server" resource which contains one intercepted and one non-intercepted
 * resource methods.
 *
 * @author Michal Gajdos
 */
@Path("server")
public class ServerResource {

    /**
     * Resource method is not intercepted itself.
     */
    @GET
    public String get() {
        return "ServerResource: Non-intercepted method invoked\n";
    }

    /**
     * Resource method is intercepted by {@code ResourceInterceptor}.
     */
    @GET
    @Path("intercepted")
    @Intercept
    public String getIntercepted() {
        return "ServerResource: Intercepted method invoked\n";
    }
}
