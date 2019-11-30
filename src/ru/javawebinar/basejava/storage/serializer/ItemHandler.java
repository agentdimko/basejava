package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ItemHandler {
    void handle() throws IOException;
}
