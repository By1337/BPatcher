package org.by1337.bpatcher.util;

import org.objectweb.asm.tree.AbstractInsnNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstractInsnNodeIterator implements Iterator<AbstractInsnNode> {
    private final AbstractInsnNode[] array;
    private int currentIndex = 0;

    public AbstractInsnNodeIterator(AbstractInsnNode[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < array.length;
    }

    @Override
    public AbstractInsnNode next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return array[currentIndex++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove operation is not supported.");
    }
}