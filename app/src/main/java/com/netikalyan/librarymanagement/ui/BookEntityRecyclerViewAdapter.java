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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.BookEntity;
import com.netikalyan.librarymanagement.data.OnListFragmentInteractionListener;

import java.util.List;

public class BookEntityRecyclerViewAdapter
        extends RecyclerView.Adapter<BookEntityRecyclerViewAdapter.ViewHolder> {

    private List<BookEntity> mBookList;
    private final OnListFragmentInteractionListener mListener;

    BookEntityRecyclerViewAdapter(List<BookEntity> books,
                                  OnListFragmentInteractionListener listener) {
        mBookList = books;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bookentity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mBookList.get(position);
        holder.mBookIdView.setText(String.valueOf(mBookList.get(position).getBookID()));
        holder.mTitleView.setText(mBookList.get(position).getTitle());
        holder.mAuthorView.setText(mBookList.get(position).getAuthor());
        holder.mPriceView.setText(String.valueOf(mBookList.get(position).getPrice()));
        holder.mAvailableView.setText(String.valueOf(mBookList.get(position).getAvailable()));

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mBookList)
            return 0;
        else
            return mBookList.size();
    }

    void setBookList(List<BookEntity> bookEntities) {
        mBookList = bookEntities;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mBookIdView;
        final TextView mTitleView;
        final TextView mAuthorView;
        final TextView mPriceView;
        final TextView mAvailableView;
        BookEntity mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mBookIdView = view.findViewById(R.id.textBookID);
            mTitleView = view.findViewById(R.id.textBookTitle);
            mAuthorView = view.findViewById(R.id.textBookAuthor);
            mPriceView = view.findViewById(R.id.textBookPrice);
            mAvailableView = view.findViewById(R.id.textBookAvailable);
        }
    }
}
