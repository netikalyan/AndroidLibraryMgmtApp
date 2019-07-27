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
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.netikalyan.librarymanagement.LibraryRepository;
import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.TransactionEntity;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private final LiveData<List<TransactionEntity>> mTransactionList;
    private final LibraryRepository mRepository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        Log.e(application.getString(R.string.app_name), "TransactionViewModel");
        mRepository = new LibraryRepository(application);
        mTransactionList = mRepository.getAllTransactions();
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return mTransactionList;
    }

    public void addNewTransaction(TransactionEntity transaction) {
        mRepository.loanBookToMember(transaction);
    }

    public void updateTransaction(TransactionEntity transaction) {
        mRepository.updateTransactionDetails(transaction);
    }

    public void deleteTransaction(TransactionEntity transaction) {
        mRepository.deleteTransaction(transaction);
    }

    public void deleteAllTransactions() {
        mRepository.deleteAllTransactions();
    }

    public TransactionEntity searcbTransaction(int transaactionID) {
        return mRepository.searchTransaction(transaactionID);
    }
}
