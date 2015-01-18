package sk.dejavu.blog.examples.intercepting.intercept;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;

import org.jvnet.hk2.annotations.Service;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;

import sk.dejavu.blog.examples.intercepting.providers.StringProvider;
import sk.dejavu.blog.examples.intercepting.resources.ServerResource;

/**
 * @author Michal Gajdos
 */
@Service
public class MyInterceptionService implements org.glassfish.hk2.api.InterceptionService {

    private static final ProviderInterceptor PROVIDER_INTERCEPTOR = new ProviderInterceptor();
    private static final ResourceInterceptor RESOURCE_INTERCEPTOR = new ResourceInterceptor();

    private static final List<MethodInterceptor> PROVIDER_METHOD_INTERCEPTORS =
            Collections.<MethodInterceptor>singletonList(PROVIDER_INTERCEPTOR);
    private static final List<ConstructorInterceptor> PROVIDER_CONSTRUCTOR_INTERCEPTORS =
            Collections.<ConstructorInterceptor>singletonList(PROVIDER_INTERCEPTOR);

    private static final List<MethodInterceptor> RESOURCE_METHOD_INTERCEPTORS =
            Collections.<MethodInterceptor>singletonList(RESOURCE_INTERCEPTOR);

    @Override
    public Filter getDescriptorFilter() {
        // We're only interested in classes (resources, providers) from this applications packages.
        return new Filter() {
            @Override
            public boolean matches(final Descriptor d) {
                final String clazz = d.getImplementation();
                return clazz.startsWith("sk.dejavu.blog.examples.intercepting.resources")
                        || clazz.startsWith("sk.dejavu.blog.examples.intercepting.providers");
            }
        };
    }

    @Override
    public List<MethodInterceptor> getMethodInterceptors(final Method method) {
        // Apply interceptors only to methods annotated with @Intercept.
        if (method.isAnnotationPresent(Intercept.class)) {
            final Class<?> clazz = method.getDeclaringClass();

            if (clazz == StringProvider.class) {
                // Provider specific interceptors.
                return PROVIDER_METHOD_INTERCEPTORS;
            } else if (clazz == ServerResource.class) {
                // Resource specific interceptors.
                return RESOURCE_METHOD_INTERCEPTORS;
            }
        }
        return null;
    }

    @Override
    public List<ConstructorInterceptor> getConstructorInterceptors(final Constructor<?> constructor) {
        // Apply interceptors only to constructors annotated with @Intercept.
        if (constructor.isAnnotationPresent(Intercept.class)) {
            // Since only StringProvider has annotated constructor return provider specific interceptors.
            return PROVIDER_CONSTRUCTOR_INTERCEPTORS;
        }
        return null;
    }
}
