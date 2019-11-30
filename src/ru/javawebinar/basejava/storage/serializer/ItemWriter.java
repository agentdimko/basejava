package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ItemWriter<E> {
    void write(E element) throws IOException;
}
