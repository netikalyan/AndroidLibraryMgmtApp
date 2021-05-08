/*
 * MIT License
 *
 * Copyright (c) 2018 netikalyan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.netikalyan.librarymanagement.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.netikalyan.librarymanagement.LibraryRepository;
import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.BookEntity;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private final LiveData<List<BookEntity>> mBookList;
    private final LibraryRepository mRepository;

    public BookViewModel(@NonNull Application application) {
        super(application);
        Log.d(application.getString(R.string.app_name), "BookViewModel");
        mRepository = new LibraryRepository(application);
        mBookList = mRepository.getAllBooks();
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return mBookList;
    }

    public void addNewBook(BookEntity book) {
        mRepository.addNewBook(book);
    }

    public void updateBook(BookEntity book) {
        mRepository.updateBookDetails(book);
    }

    public void deleteBook(BookEntity book) {
        mRepository.deleteBook(book);
    }

    public void deleteAllBooks() {
        mRepository.deleteAllBooks();
    }

    public BookEntity searchBookByID(int bookID) {
        return mRepository.searchBookByID(bookID);
    }

    public BookEntity[] searchBookByTitle(String bookTitle) {
        return mRepository.searchBookByTitle(bookTitle);
    }

    public BookEntity[] searchBookByAuthor(String bookAuthor) {
        return mRepository.searchBookByAuthor(bookAuthor);
    }
}
