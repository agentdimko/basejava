package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ItemHReader<E> {
    E read() throws IOException;
}
