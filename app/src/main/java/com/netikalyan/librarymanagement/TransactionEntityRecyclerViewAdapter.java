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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TransactionEntityRecyclerViewAdapter
        extends RecyclerView.Adapter<TransactionEntityRecyclerViewAdapter.ViewHolder> {

    private List<TransactionEntity> mTransactionList;
    private final OnListFragmentInteractionListener mListener;

    TransactionEntityRecyclerViewAdapter(List<TransactionEntity> transactions,
                                         OnListFragmentInteractionListener listener) {
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
                .setText(String.valueOf(mTransactionList.get(position).getBookID()));
        holder.mBookView.setText(mTransactionList.get(position).getBookID());
        holder.mMemberView.setText(mTransactionList.get(position).getMemberID());
        holder.mLoanDateView
                .setText(String.valueOf(mTransactionList.get(position).getDateOfLoan()));
        holder.mReturnDateView
                .setText(String.valueOf(mTransactionList.get(position).getDateOfReturn()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mTransactionList)
            return 0;
        else
            return mTransactionList.size();
    }

    void setTransactionList(List<TransactionEntity> transactionEntities) {
        mTransactionList = transactionEntities;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mTransactionIdView;
        final TextView mBookView;
        final TextView mMemberView;
        final TextView mLoanDateView;
        final TextView mReturnDateView;
        TransactionEntity mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mTransactionIdView = view.findViewById(R.id.textTransactionID);
            mBookView = view.findViewById(R.id.textTransactionBookID);
            mMemberView = view.findViewById(R.id.textTransactionMemberID);
            mLoanDateView = view.findViewById(R.id.textTransactionLoanDate);
            mReturnDateView = view.findViewById(R.id.textTransactionReturnDate);
        }
    }
}
