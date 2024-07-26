package org.by1337.bpatcher.patcher.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for specifying patcher classes.
 * <p>
 * A class annotated with this annotation must implement the {@link org.by1337.bpatcher.patcher.api.Patcher} interface.
 * </p>
 *
 * <p>
 * The annotation is used by an annotation processor that writes the path to the annotated class in the bpatcher-lookup.json file.
 * </p>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Patch {
}
