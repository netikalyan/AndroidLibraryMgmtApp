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

package com.netikalyan.librarymanagement;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

public class BookFragment extends Fragment {

    private BookViewModel mViewModel;
    private EditText editBookID, editBookTitle, editBookAuthor, editBookPrice, editBookAvailable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.book_fragment, container, false);
        editBookID = rootView.findViewById(R.id.editBookID);
        editBookTitle = rootView.findViewById(R.id.editBookTitle);
        editBookAuthor = rootView.findViewById(R.id.editBookAuthor);
        editBookPrice = rootView.findViewById(R.id.editBookPrice);
        editBookAvailable = rootView.findViewById(R.id.editBookAvailable);
        if (null != getArguments()) {
            setBook((BookEntity) getArguments().getParcelable(EntityItemActivity.DB_ITEM));
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(@Nullable List<BookEntity> bookEntities) {

            }
        });
    }

    public void addBook(BookEntity book) {
        mViewModel.addNewBook(book);
    }

    public BookEntity searchBook(int bookID) {
        return mViewModel.searchBook(bookID);
    }

    public void modifyBook(BookEntity book) {
        mViewModel.updateBook(book);
    }

    public void deleteBook(BookEntity book) {
        mViewModel.deleteBook(book);
    }

    public int getBookID() {
        return Integer.parseInt(editBookID.getText().toString());
    }

    public String getBookTitle() {
        return editBookTitle.getText().toString();
    }

    public String getBookAuthor() {
        return editBookAuthor.getText().toString();
    }

    public float getBookPrice() {
        return Float.parseFloat(editBookPrice.getText().toString());
    }

    public int getBookAvailable() {
        return Integer.parseInt(editBookAvailable.getText().toString());
    }

    @NonNull
    public BookEntity getBookDetails() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookID(getBookID());
        bookEntity.setTitle(getBookTitle());
        bookEntity.setAuthor(getBookAuthor());
        bookEntity.setPrice(getBookPrice());
        bookEntity.setAvailable(getBookAvailable());
        return bookEntity;
    }

    private void setBook(@NonNull BookEntity book) {
        editBookID.setText(String.valueOf(book.getBookID()));
        editBookTitle.setText(book.getTitle());
        editBookAuthor.setText(book.getAuthor());
        editBookPrice.setText(String.valueOf(book.getPrice()));
        editBookAvailable.setText(String.valueOf(book.getAvailable()));
    }
}
