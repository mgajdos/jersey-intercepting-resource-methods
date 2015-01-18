package sk.dejavu.blog.examples.intercepting.intercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Michal Gajdos
 */
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Intercept {
}
