package org.by1337.bpatcher.patcher.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.jar.JarFile;

/**
 * Annotation for including a class in the server core.
 * <p>
 * A class annotated with this annotation will be added to the system class loader
 * using the method {@link java.lang.instrument.Instrumentation#appendToSystemClassLoaderSearch(JarFile)}.
 * </p>
 *
 * <p>
 * This is applied at the source code level and only to classes.
 * </p>
 *
 * @see java.lang.instrument.Instrumentation#appendToSystemClassLoaderSearch(JarFile)
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Inject {
}
