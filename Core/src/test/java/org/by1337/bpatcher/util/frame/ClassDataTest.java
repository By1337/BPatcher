package org.by1337.bpatcher.util.frame;

import org.by1337.bpatcher.util.clazz.ClassData;
import org.by1337.bpatcher.util.clazz.ClassFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClassDataTest {
    private ClassFinder finder;
    @BeforeEach
    void init() {
        finder = new ClassFinder(Collections.emptyMap());
    }

    @Test
    void isAssignableFrom() {
        assertEquals(
                Number.class.isAssignableFrom(Integer.class),
                asClassData(Number.class).isAssignableFrom(asClassData(Integer.class))
        );
        assertEquals(
                Integer.class.isAssignableFrom(Number.class),
                asClassData(Integer.class).isAssignableFrom(asClassData(Number.class))
        );
        assertEquals(
                Object.class.isAssignableFrom(String.class),
                asClassData(Object.class).isAssignableFrom(asClassData(String.class))
        );
        assertEquals(
                String.class.isAssignableFrom(Object.class),
                asClassData(String.class).isAssignableFrom(asClassData(Object.class))
        );
        assertEquals(
                Iterable.class.isAssignableFrom(ArrayList.class),
                asClassData(Iterable.class).isAssignableFrom(asClassData(ArrayList.class))
        );
        assertEquals(
                List.class.isAssignableFrom(ArrayList.class),
                asClassData(List.class).isAssignableFrom(asClassData(ArrayList.class))
        );
        assertEquals(
                ArrayList.class.isAssignableFrom(List.class),
                asClassData(ArrayList.class).isAssignableFrom(asClassData(List.class))
        );
        assertEquals(
                Object.class.isAssignableFrom(Cloneable.class),
                asClassData(Object.class).isAssignableFrom(asClassData(Cloneable.class))
        );
        assertEquals(
                Map.class.isAssignableFrom(HashMap.class),
                asClassData(Map.class).isAssignableFrom(asClassData(HashMap.class))
        );
        assertEquals(
                HashMap.class.isAssignableFrom(Map.class),
                asClassData(HashMap.class).isAssignableFrom(asClassData(Map.class))
        );

    }

    private ClassData asClassData(Class<?> clazz) {
        ClassData classData = finder.findClass(clazz.getName().replace(".", "/"));
        assertNotNull(classData, "ClassData should not be null for " + clazz.getName());
        return classData;
    }
}