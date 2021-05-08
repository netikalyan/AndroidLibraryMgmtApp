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

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.BookEntity;
import com.netikalyan.librarymanagement.data.MemberEntity;
import com.netikalyan.librarymanagement.data.OnListFragmentInteractionListener;
import com.netikalyan.librarymanagement.data.TransactionEntity;

import java.util.Date;
import java.util.List;

public class TransactionEntityRecyclerViewAdapter
        extends RecyclerView.Adapter<TransactionEntityRecyclerViewAdapter.ViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private List<TransactionEntity> mTransactionList;
    private final OnListFragmentInteractionListener mListener;

    TransactionEntityRecyclerViewAdapter(@Nullable List<TransactionEntity> transactions,
                                         @NonNull OnListFragmentInteractionListener listener) {
        mTransactionList = transactions;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transactionentity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mTransactionList.get(position);
        holder.mTransactionIdView
                .setText(String.valueOf(mTransactionList.get(position).getTransactionID()));
        holder.mBookNameView.setText(mTransactionList.get(position).getBookTitle());
        holder.mBookIDView.setText(String.valueOf(mTransactionList.get(position).getBookID()));
        holder.mMemberNameView.setText(mTransactionList.get(position).getMemberName());
        holder.mMemberIDView.setText(String.valueOf(mTransactionList.get(position).getMemberID()));
        holder.mLoanDateView.setText(
                DateFormat.format(DATE_FORMAT, mTransactionList.get(position).getDateOfLoan()));
        Date returnDate = mTransactionList.get(position).getDateOfReturn();
        if (null != returnDate) {
            holder.mReturnDateView.setText(DateFormat.format(DATE_FORMAT, returnDate));
        }

        holder.mView.setOnClickListener(v -> mListener.onListFragmentInteraction(holder.mItem));
    }

    @Override
    public int getItemCount() {
        if (null == mTransactionList)
            return 0;
        else
            return mTransactionList.size();
    }

    void setTransactionList(@Nullable List<TransactionEntity> transactionEntities) {
        if (null != transactionEntities) {
            mTransactionList = transactionEntities;
            notifyDataSetChanged();
        }
    }

    void setBookList(@NonNull List<BookEntity> bookEntities) {
        if (null != mTransactionList) {
            for (TransactionEntity entity : mTransactionList) {
                for (BookEntity book : bookEntities) {
                    if (book.getBookID() == entity.getBookID())
                        entity.setBookTitle(book.getTitle());
                }
            }
            notifyDataSetChanged();
        }
    }

    void setMemberList(@NonNull List<MemberEntity> memberEntities) {
        if (null != mTransactionList) {
            for (TransactionEntity entity : mTransactionList) {
                for (MemberEntity member : memberEntities) {
                    if (member.getMemberID() == entity.getMemberID())
                        entity.setMemberName(member.getName());
                }
            }
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mTransactionIdView;
        final TextView mBookNameView;
        final TextView mBookIDView;
        final TextView mMemberNameView;
        final TextView mMemberIDView;
        final TextView mLoanDateView;
        final TextView mReturnDateView;
        TransactionEntity mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mTransactionIdView = view.findViewById(R.id.textTransactionID);
            mBookNameView = view.findViewById(R.id.textTransactionBookName);
            mBookIDView = view.findViewById(R.id.textTransactionBookID);
            mMemberNameView = view.findViewById(R.id.textTransactionMemberName);
            mMemberIDView = view.findViewById(R.id.textTransactionMemberID);
            mLoanDateView = view.findViewById(R.id.textTransactionLoanDate);
            mReturnDateView = view.findViewById(R.id.textTransactionReturnDate);
        }
    }
}
