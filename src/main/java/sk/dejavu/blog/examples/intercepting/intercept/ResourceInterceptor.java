package sk.dejavu.blog.examples.intercepting.intercept;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Michal Gajdos
 */
public class ResourceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        // Modify the entity returned by the invoked resource method by appending additional message.
        return methodInvocation.proceed()
                + "   ResourceInterceptor: Method \""
                    + methodInvocation.getMethod().getName() + "\" intercepted\n";
    }
}
