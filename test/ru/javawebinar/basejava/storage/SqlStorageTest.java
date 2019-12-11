package ru.javawebinar.basejava.storage;

import static ru.javawebinar.basejava.Config.getInstance;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SQLStorage(getInstance().getDbUrl(), getInstance().getUser(), getInstance().getPassword()));
    }
}
