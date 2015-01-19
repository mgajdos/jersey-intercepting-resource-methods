package sk.dejavu.blog.examples.intercepting;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import sk.dejavu.blog.examples.intercepting.intercept.MyInterceptionBinder;
import sk.dejavu.blog.examples.intercepting.providers.StringProvider;

/**
 * @author Michal Gajdos
 */
public class InterceptingTest extends JerseyTest {

    @Override
    protected javax.ws.rs.core.Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new Application();
    }

    @Test
    public void serverResourceNonInterceptedMethod() throws Exception {
        final Response response = target().path("server").request().get();

        assertThat(response.readEntity(String.class),
                is("ServerResource: Non-intercepted method invoked\n"));
    }

    @Test
    public void serverResourceInterceptedMethod() throws Exception {
        final Response response = target("server").path("intercepted").request().get();

        assertThat(response.readEntity(String.class),
                is("ServerResource: Intercepted method invoked\n"
                        + "   ResourceInterceptor: Method \"getIntercepted\" intercepted\n"));
    }

    @Test
    public void clientResource() throws Exception {
        final Response response = target().path("client").request().get();

        assertThat(response.readEntity(String.class),
                is("ClientResource: Invoke request to non-intercepted server resource method\n"
                        + "   ServerResource: Non-intercepted method invoked\n"
                        + "   StringProvider(Client): Entity reading intercepted\n"));
    }

    @Test
    public void clientFetchingServerResource() throws Exception {
        final Response response = target()
                .register(StringProvider.class)
                .register(new MyInterceptionBinder())
                .path("server")
                .request().get();

        assertThat(response.readEntity(String.class),
                is("ServerResource: Non-intercepted method invoked\n"
                        + "   StringProvider(Client): Entity reading intercepted\n"));
    }
}
