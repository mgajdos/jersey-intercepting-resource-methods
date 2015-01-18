package sk.dejavu.blog.examples.intercepting.intercept;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.ConstructorInvocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import sk.dejavu.blog.examples.intercepting.providers.StringProvider;

/**
 * @author Michal Gajdos
 */
public class ProviderInterceptor implements MethodInterceptor, ConstructorInterceptor {

    @Override
    public Object construct(final ConstructorInvocation invocation) throws Throwable {
        final Object proceed = invocation.proceed();

        // After an instance of StringProvider is ready, set a name to that instance.
        if (proceed instanceof StringProvider) {
            ((StringProvider) proceed).setProviderName(StringProvider.class.getSimpleName());
        }

        return proceed;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        // This method intercepts either StringProvider.isReadable or StringProvider.isWriteable methods.

        // First argument of intercepted methods is class of instance to be written or produced.
        final Object type = invocation.getArguments()[0];

        // Do not invoke the provider's method itself. Return true if a String is about to be written
        // or produced, false otherwise.
        return type == String.class;
    }
}
