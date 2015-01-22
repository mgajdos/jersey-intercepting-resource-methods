package sk.dejavu.blog.examples.intercepting.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.server.Uri;

import sk.dejavu.blog.examples.intercepting.intercept.ClientConfig;

/**
 * @author Michal Gajdos
 */
@Path("client")
public class ClientResource {

    @ClientConfig
    @Uri("server")
    private WebTarget server;

    /**
     * Resource method is not intercepted itself.
     *
     * In the injected client the {@code StringProvider} is registered
     * and the provider is intercepted (constructor, isReadable).
     */
    @GET
    public String get() {
        return "ClientResource: Invoke request to non-intercepted"
                + " server resource method\n"
                + "   " + server.request().get(String.class);
    }
}
