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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.OnListFragmentInteractionListener;
import com.netikalyan.librarymanagement.viewmodel.BookViewModel;
import com.netikalyan.librarymanagement.viewmodel.MemberViewModel;
import com.netikalyan.librarymanagement.viewmodel.TransactionViewModel;

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
                    new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(TransactionViewModel.class);
            transactionViewModel.getAllTransactions()
                    .observe(this, transactionEntities -> {
                        if (null != transactionEntities && 0 < transactionEntities.size())
                            mAdapter.setTransactionList(transactionEntities);
                    });

            mAdapter = new TransactionEntityRecyclerViewAdapter(
                    transactionViewModel.getAllTransactions().getValue(), mListener);
            recyclerView.setAdapter(mAdapter);

            BookViewModel bookViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(BookViewModel.class);
            bookViewModel.getAllBooks().observe(this, bookEntities -> {
                if (null != bookEntities && 0 < bookEntities.size())
                    mAdapter.setBookList(bookEntities);
            });

            MemberViewModel memberViewModel =
                    new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MemberViewModel.class);
            memberViewModel.getAllMembers().observe(this, memberEntities -> {
                if (null != memberEntities && 0 < memberEntities.size())
                    mAdapter.setMemberList(memberEntities);
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
