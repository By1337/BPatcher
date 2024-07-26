package org.by1337.bpatcher.util;
@FunctionalInterface
public interface FunctionToByteArray<T> {
    byte[] apply(T t);
}
