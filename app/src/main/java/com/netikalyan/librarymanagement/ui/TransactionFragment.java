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

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.BookEntity;
import com.netikalyan.librarymanagement.data.ITransactionManagement;
import com.netikalyan.librarymanagement.data.LibraryException;
import com.netikalyan.librarymanagement.data.MemberEntity;
import com.netikalyan.librarymanagement.data.TransactionEntity;
import com.netikalyan.librarymanagement.viewmodel.BookViewModel;
import com.netikalyan.librarymanagement.viewmodel.MemberViewModel;
import com.netikalyan.librarymanagement.viewmodel.TransactionViewModel;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class TransactionFragment extends Fragment implements ITransactionManagement {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private TransactionViewModel mViewModel;
    private BookViewModel mBookViewModel;
    private MemberViewModel mMemberViewModel;
    private EditText editTransactionID, editTransactionLoanDate, editTransactionReturnDate;
    private AutoCompleteTextView editTransactionBookName, editTransactionMemberName;
    private TextView textTransactionBookID, textTransactionMemberID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction_edit, container, false);
        editTransactionID = rootView.findViewById(R.id.editTransactionID);
        textTransactionBookID = rootView.findViewById(R.id.textTransactionBookID);
        editTransactionBookName = rootView.findViewById(R.id.editTransactionBookName);
        textTransactionMemberID = rootView.findViewById(R.id.textTransactionMemberID);
        editTransactionMemberName = rootView.findViewById(R.id.editTransactionMemberName);
        editTransactionLoanDate = rootView.findViewById(R.id.editTransactionLoanDate);
        editTransactionReturnDate = rootView.findViewById(R.id.editTransactionReturnDate);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(TransactionViewModel.class);
        mBookViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(BookViewModel.class);
        mMemberViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MemberViewModel.class);
        mViewModel.getAllTransactions().observe(this, transactionEntities -> {
            // TODO: anything to do here ?
        });
        mBookViewModel.getAllBooks().observe(this, bookEntities -> {
            if (null != bookEntities && 0 < bookEntities.size()) {
                editTransactionBookName.setAdapter(
                        new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_list_item_1,
                                bookEntities));
                editTransactionBookName.setOnItemClickListener(
                        (parent, view, position, id) -> {
                            Log.d(getString(R.string.app_name),
                                    parent.getItemAtPosition(position).toString());
                            BookEntity[] book = mBookViewModel.searchBookByTitle(
                                    editTransactionBookName.getText().toString());
                            textTransactionBookID
                                    .setText(String.valueOf(book[0].getBookID()));
                        });
            }
        });
        mMemberViewModel.getAllMembers().observe(this, memberEntities -> {
            if (null != memberEntities && 0 < memberEntities.size()) {
                editTransactionMemberName.setAdapter(
                        new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_list_item_1,
                                memberEntities));
                editTransactionMemberName.setOnItemClickListener(
                        (parent, view, position, id) -> {
                            Log.d(getString(R.string.app_name),
                                    parent.getItemAtPosition(position).toString());
                            MemberEntity[] member = mMemberViewModel.searchMemberByName(
                                    editTransactionMemberName.getText().toString());
                            textTransactionMemberID
                                    .setText(String.valueOf(member[0].getMemberID()));
                        });
            }
        });
        if (null != getArguments()) {
            set(Objects.requireNonNull(getArguments().getParcelable(EntityItemActivity.DB_ITEM)));
        }
    }

    @Override
    public void add(@NonNull TransactionEntity transaction) {
        mViewModel.addNewTransaction(transaction);
    }

    @Override
    public void modify(@NonNull TransactionEntity transaction) {
        mViewModel.updateTransaction(transaction);
    }

    @Override
    public void delete(@NonNull TransactionEntity transaction) {
        mViewModel.addNewTransaction(transaction);
    }

    @Override
    public TransactionEntity search(int transactionID) {
        return mViewModel.searcbTransaction(transactionID);
    }

    @Override
    @NonNull
    public TransactionEntity get() throws LibraryException {
        TransactionEntity transactionEntity = new TransactionEntity();
        if (-1 != getTransactionID())
            transactionEntity.setTransactionID(getTransactionID());
        else
            throw new LibraryException(LibraryException.Constants.TRANSACTION_ID_MISSING);
        if (-1 != getTransactionBookID())
            transactionEntity.setBookID(getTransactionBookID());
        else
            throw new LibraryException(LibraryException.Constants.BOOK_ID_MISSING);
        if (null != getTransactionBookTitle())
            transactionEntity.setBookTitle(getTransactionBookTitle());
        else
            throw new LibraryException(LibraryException.Constants.BOOK_TITLE_MISSING);
        if (-1 != getTransactionMemberID())
            transactionEntity.setMemberID(getTransactionMemberID());
        else
            throw new LibraryException(LibraryException.Constants.MEMBER_ID_MISSING);
        if (null != getTransactionMemberName())
            transactionEntity.setMemberName(getTransactionMemberName());
        else
            throw new LibraryException(LibraryException.Constants.MEMBER_NAME_MISSING);
        if (null != getTransactionLoanDate())
            transactionEntity.setDateOfLoan(getTransactionLoanDate());
        else
            throw new LibraryException(LibraryException.Constants.TRANSACTION_LOAN_DATE_MISSING);
        transactionEntity.setDateOfReturn(getTransactionReturnDate());
        return transactionEntity;
    }

    @Override
    public void set(@NonNull TransactionEntity transaction) {
        editTransactionID.setText(String.valueOf(transaction.getTransactionID()));
        textTransactionBookID.setText(String.valueOf(transaction.getBookID()));
        BookEntity bookEntity = mBookViewModel.searchBookByID(transaction.getBookID());
        editTransactionBookName.setText(bookEntity.getTitle());
        transaction.setBookTitle(bookEntity.getTitle());
        textTransactionMemberID.setText(String.valueOf(transaction.getMemberID()));
        MemberEntity memberEntity = mMemberViewModel.searchMember(transaction.getMemberID());
        editTransactionMemberName.setText(memberEntity.getName());
        transaction.setMemberName(memberEntity.getName());
        editTransactionLoanDate
                .setText(DateFormat.format(DATE_FORMAT, transaction.getDateOfLoan()));
        editTransactionReturnDate
                .setText(DateFormat.format(DATE_FORMAT, transaction.getDateOfReturn()));
    }

    private int getTransactionID() {
        if (!editTransactionID.getText().toString().isEmpty())
            return Integer.parseInt(editTransactionID.getText().toString());
        else
            Log.e(getString(R.string.app_name), "Missing Transaction ID");
        return -1;
    }

    private int getTransactionMemberID() {
        if (!textTransactionMemberID.getText().toString().isEmpty())
            return Integer.parseInt(textTransactionMemberID.getText().toString());
        else
            Log.e(getString(R.string.app_name), "Missing Member ID");
        return -1;
    }

    @Nullable
    private String getTransactionMemberName() {
        if (!editTransactionMemberName.getText().toString().isEmpty())
            return editTransactionMemberName.getText().toString();
        else
            Log.e(getString(R.string.app_name), "Missing Member Name");
        return null;
    }

    private int getTransactionBookID() {
        if (!textTransactionBookID.getText().toString().isEmpty())
            return Integer.parseInt(textTransactionBookID.getText().toString());
        else
            Log.e(getString(R.string.app_name), "Missing Book ID");
        return -1;
    }

    @Nullable
    private String getTransactionBookTitle() {
        if (!editTransactionBookName.getText().toString().isEmpty())
            return editTransactionBookName.getText().toString();
        else
            Log.e(getString(R.string.app_name), "Missing Book Name");
        return null;
    }

    @Nullable
    private Date getTransactionLoanDate() {
        Log.d(getString(R.string.app_name),
                "Loan Date = " + editTransactionLoanDate.getText().toString());
        if (!editTransactionLoanDate.getText().toString().isEmpty()) {
            try {
                return DateFormat.getDateFormat(this.getContext())
                        .parse(editTransactionLoanDate.getText().toString());
            } catch (ParseException e) {
                Log.e(getString(R.string.app_name), "Parsing error in loan date " + e.getMessage());
            }
        } else {
            Log.e(getString(R.string.app_name), "Missing Loan Date");
        }
        return null;
    }

    @Nullable
    private Date getTransactionReturnDate() {
        Log.v(getString(R.string.app_name),
                "Return Date = " + editTransactionReturnDate.getText().toString());
        if (null != editTransactionReturnDate.getText() &&
                0 != editTransactionReturnDate.getText().length()) {
            try {
                return DateFormat.getDateFormat(this.getContext())
                        .parse(editTransactionReturnDate.getText().toString());
            } catch (ParseException e) {
                Log.e(getString(R.string.app_name),
                        "Parsing error in return date " + e.getMessage());
            }
        } else {
            Log.e(getString(R.string.app_name), "Missing Return Date");
        }
        return null;
    }
}
