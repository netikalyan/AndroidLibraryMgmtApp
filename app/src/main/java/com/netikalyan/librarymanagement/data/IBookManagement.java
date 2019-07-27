package com.netikalyan.librarymanagement.data;

public interface IBookManagement {
    void addBook(BookEntity book);

    BookEntity searchBook(int bookID);

    void modifyBook(BookEntity book);

    void deleteBook(BookEntity book);
}
