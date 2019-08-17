package com.netikalyan.librarymanagement.data;

public interface IEntityManagement<V extends ILibraryEntity> {
    enum Action {
        ADD,
        MODIFY
    }
    void add(V entity);
    V search(int _id);
    void modify(V entity);
    void delete(V entity);
    V get() throws LibraryException;
    void set(V v);
}
