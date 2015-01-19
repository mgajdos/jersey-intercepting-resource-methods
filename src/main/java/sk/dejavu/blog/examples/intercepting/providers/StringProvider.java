package sk.dejavu.blog.examples.intercepting.providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.RuntimeType;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.message.internal.AbstractMessageReaderWriterProvider;

import sk.dejavu.blog.examples.intercepting.intercept.Intercept;

/**
 * Message body provider (reader and writer) which by default (if not intercepted)
 * says that it cannot process anything (see isReadable, isWriteable methods).
 *
 * After an instance of the constructor is created the {@code ProviderInterceptor}
 * which also intercepts construction of the instance sets provider name for the object.
 *
 * @author Michal Gajdos
 */
// Disable package scanning.
// @Provider
public class StringProvider extends AbstractMessageReaderWriterProvider<String> {

    @Context
    private Configuration config;

    private String providerName;

    @Intercept
    public StringProvider() {
    }

    public void setProviderName(final String providerName) {
        this.providerName = providerName;
    }

    @Override
    @Intercept
    public boolean isReadable(final Class<?> type,
                              final Type genericType,
                              final Annotation[] annotations,
                              final MediaType mediaType) {
        return false;
    }

    @Override
    public String readFrom(final Class<String> type,
                           final Type genericType,
                           final Annotation[] annotations,
                           final MediaType mediaType,
                           final MultivaluedMap<String, String> httpHeaders,
                           final InputStream entityStream) throws IOException, WebApplicationException {

        return readFromAsString(entityStream, mediaType)
                + "   " + providerName + "(" + getRuntime() + "): Entity reading intercepted\n";
    }

    @Override
    @Intercept
    public boolean isWriteable(final Class<?> type,
                               final Type genericType,
                               final Annotation[] annotations,
                               final MediaType mediaType) {
        return false;
    }

    @Override
    public void writeTo(final String str,
                        final Class<?> type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders,
                        final OutputStream entityStream) throws IOException, WebApplicationException {
        final String entity = str
                + "   " + providerName + "(" + getRuntime() + "): Entity writing intercepted\n";

        writeToAsString(entity, entityStream, mediaType);
    }

    private String getRuntime() {
        return config.getRuntimeType() == RuntimeType.SERVER ? "Server" : "Client";
    }
}
