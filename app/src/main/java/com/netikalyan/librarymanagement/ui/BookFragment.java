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

package com.netikalyan.librarymanagement.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.BookEntity;
import com.netikalyan.librarymanagement.data.IBookManagement;
import com.netikalyan.librarymanagement.data.LibraryException;
import com.netikalyan.librarymanagement.viewmodel.BookViewModel;

import java.util.Objects;

public class BookFragment extends Fragment implements IBookManagement {

    private BookViewModel mViewModel;
    private EditText editBookID, editBookTitle, editBookAuthor, editBookPrice, editBookAvailable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_edit, container, false);
        editBookID = rootView.findViewById(R.id.editBookID);
        editBookTitle = rootView.findViewById(R.id.editBookTitle);
        editBookAuthor = rootView.findViewById(R.id.editBookAuthor);
        editBookPrice = rootView.findViewById(R.id.editBookPrice);
        editBookAvailable = rootView.findViewById(R.id.editBookAvailable);
        if (null != getArguments()) {
            set(Objects.requireNonNull(getArguments().getParcelable(EntityItemActivity.DB_ITEM)));
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mViewModel.getAllBooks().observe(this, bookEntities -> {
            // TODO: anything to do here ?
            Log.d(getString(R.string.app_name), "Change in Books. Observer called");
        });
    }

    @Override
    public void add(BookEntity book) {
        mViewModel.addNewBook(book);
    }

    @Override
    public BookEntity search(int bookID) {
        return mViewModel.searchBookByID(bookID);
    }

    @Override
    public void modify(BookEntity book) {
        mViewModel.updateBook(book);
    }

    @Override
    public void delete(BookEntity book) {
        mViewModel.deleteBook(book);
    }

    private int getBookID() throws LibraryException {
        String bookID = editBookID.getText().toString();
        if (!bookID.isEmpty())
            return Integer.parseInt(bookID);
        throw new LibraryException(LibraryException.Constants.BOOK_ID_MISSING);
    }

    private String getBookTitle() throws LibraryException {
        String title = editBookTitle.getText().toString();
        if (!title.isEmpty())
            return title;
        throw new LibraryException(LibraryException.Constants.BOOK_TITLE_MISSING);
    }

    private String getBookAuthor() throws LibraryException {
        String author = editBookAuthor.getText().toString();
        if (!author.isEmpty())
            return author;
        throw new LibraryException(LibraryException.Constants.BOOK_AUTHOR_MISSING);
    }

    private float getBookPrice() throws LibraryException {
        String price = editBookPrice.getText().toString();
        if (!price.isEmpty())
            return Float.parseFloat(editBookPrice.getText().toString());
        throw new LibraryException(LibraryException.Constants.BOOK_PRICE_MISSING);
    }

    private int getBookAvailable() throws LibraryException {
        String availableCopies = editBookAvailable.getText().toString();
        if (!availableCopies.isEmpty())
            return Integer.parseInt(availableCopies);
        throw new LibraryException(LibraryException.Constants.BOOK_AVAILABLE_COPIES_MISSING);
    }

    @Override
    @NonNull
    public BookEntity get() throws LibraryException {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookID(getBookID());
        bookEntity.setTitle(getBookTitle());
        bookEntity.setAuthor(getBookAuthor());
        bookEntity.setPrice(getBookPrice());
        bookEntity.setAvailable(getBookAvailable());
        return bookEntity;
    }

    @Override
    public void set(@NonNull BookEntity book) {
        editBookID.setText(String.valueOf(book.getBookID()));
        editBookTitle.setText(book.getTitle());
        editBookAuthor.setText(book.getAuthor());
        editBookPrice.setText(String.valueOf(book.getPrice()));
        editBookAvailable.setText(String.valueOf(book.getAvailable()));
    }
}
