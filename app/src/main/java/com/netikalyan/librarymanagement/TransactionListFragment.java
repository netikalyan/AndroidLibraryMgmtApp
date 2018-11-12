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
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TransactionListFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;
    private TransactionEntityRecyclerViewAdapter mAdapter;

    public TransactionListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactionentity_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            TransactionViewModel transactionViewModel =
                    ViewModelProviders.of(this).get(TransactionViewModel.class);
            transactionViewModel.getAllTransactions()
                    .observe(this, new Observer<List<TransactionEntity>>() {
                        @Override
                        public void onChanged(
                                @Nullable List<TransactionEntity> transactionEntities) {
                            if (null != transactionEntities && 0 < transactionEntities.size())
                                mAdapter.setTransactionList(transactionEntities);
                        }
                    });

            mAdapter = new TransactionEntityRecyclerViewAdapter(
                    transactionViewModel.getAllTransactions().getValue(), mListener);
            recyclerView.setAdapter(mAdapter);

            BookViewModel bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
            bookViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
                @Override
                public void onChanged(@Nullable List<BookEntity> bookEntities) {
                    if (null != bookEntities && 0 < bookEntities.size())
                        mAdapter.setBookList(bookEntities);
                }
            });

            MemberViewModel memberViewModel =
                    ViewModelProviders.of(this).get(MemberViewModel.class);
            memberViewModel.getAllMembers().observe(this, new Observer<List<MemberEntity>>() {
                @Override
                public void onChanged(@Nullable List<MemberEntity> memberEntities) {
                    if (null != memberEntities && 0 < memberEntities.size())
                        mAdapter.setMemberList(memberEntities);
                }
            });
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
