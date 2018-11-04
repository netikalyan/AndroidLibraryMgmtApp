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
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class TransactionFragment extends Fragment {

    private TransactionViewModel mViewModel;
    private BookViewModel mBookViewModel;
    private MemberViewModel mMemberViewModel;
    private EditText editTransactionID, editTransactionLoanDate, editTransactionReturnDate,
            editTransactionBookName, editTransactionMemberName;
    private TextView textTransactionBookID, textTransactionMemberID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.transaction_fragment, container, false);
        editTransactionID = rootView.findViewById(R.id.editTransactionID);
        textTransactionBookID = rootView.findViewById(R.id.textTransactionBookID);
        editTransactionBookName = rootView.findViewById(R.id.editTransactionBookName);
        textTransactionMemberID = rootView.findViewById(R.id.textTransactionMemberID);
        editTransactionMemberName = rootView.findViewById(R.id.editTransactionMemberName);
        editTransactionLoanDate = rootView.findViewById(R.id.editTransactionLoanDate);
        editTransactionReturnDate = rootView.findViewById(R.id.editTransactionReturnDate);
        if (null != getArguments()) {
            setTransaction((TransactionEntity) getArguments().getParcelable(EntityItemActivity.DB_ITEM));
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mMemberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        mViewModel.getAllTransactions().observe(this, new Observer<List<TransactionEntity>>() {
            @Override
            public void onChanged(@Nullable List<TransactionEntity> transactionEntities) {

            }
        });
    }

    public void loanBookToMember(TransactionEntity transaction) {
        mViewModel.addNewTransaction(transaction);
    }

    public void returnBookToLibrary(TransactionEntity transaction) {
        mViewModel.updateTransaction(transaction);
    }

    public void deleteTransaction(TransactionEntity transaction) {
        mViewModel.addNewTransaction(transaction);
    }

    public TransactionEntity search(int transactionID) {
        return mViewModel.searcbTransaction(transactionID);
    }

    @Nullable
    public TransactionEntity getTransactionDetails() {
        try {
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setTransactionID(getTransactionID());
            transactionEntity.setBookID(getTransactionBookID());
            transactionEntity.setMemberID(getTransactionMemberID());
            transactionEntity.setDateOfLoan(getTransactionLoanDate());
            transactionEntity.setDateOfReturn(getTransactionReturnDate());
            return transactionEntity;
        } catch (ParseException e) {
            return null;
        }
    }

    public int getTransactionID() {
        return Integer.parseInt(editTransactionID.getText().toString());
    }

    public int getTransactionMemberID() {
        return Integer.parseInt(textTransactionMemberID.getText().toString());
    }

    public int getTransactionBookID() {
        return Integer.parseInt(textTransactionBookID.getText().toString());
    }

    public Date getTransactionLoanDate() throws ParseException {
        return DateFormat.getInstance().parse(editTransactionLoanDate.getText().toString());
    }

    public Date getTransactionReturnDate() throws ParseException {
        return DateFormat.getInstance().parse(editTransactionReturnDate.getText().toString());
    }

    private void setTransaction(@NonNull TransactionEntity transaction) {
        editTransactionID.setText(String.valueOf(transaction.getTransactionID()));
        textTransactionBookID.setText(transaction.getBookID());
        BookEntity bookEntity = mBookViewModel.searchBook(transaction.getBookID());
        editTransactionBookName.setText(bookEntity.getTitle());
        textTransactionMemberID.setText(transaction.getMemberID());
        MemberEntity memberEntity = mMemberViewModel.searchMember(transaction.getMemberID());
        editTransactionMemberName.setText(memberEntity.getName());
        editTransactionLoanDate.setText(String.valueOf(transaction.getDateOfLoan()));
        editTransactionReturnDate.setText(String.valueOf(transaction.getDateOfReturn()));
    }
}
