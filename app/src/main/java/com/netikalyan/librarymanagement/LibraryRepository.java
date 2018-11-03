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

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class LibraryRepository {
    private BookDao mBookDao;
    private MemberDao mMemberDao;
    private TransactionDao mTransactionDao;
    private LiveData<List<BookEntity>> mListBooks;
    private LiveData<List<MemberEntity>> mListMembers;
    private LiveData<List<TransactionEntity>> mListTransactions;

    public LibraryRepository(Application application) {
        LibraryDatabase database = LibraryDatabase.getDatabase(application);
        mBookDao = database.bookDao();
        mListBooks = mBookDao.getAllBooks();
        mMemberDao = database.memberDao();
        mListMembers = mMemberDao.getAllMembers();
        mTransactionDao = database.transactionDao();
        mListTransactions = mTransactionDao.getAllTransactions();
    }

    // TODO: All DB operations to be handled in background thread.

    /**
     * BOOKS TABLE
     */
    public LiveData<List<BookEntity>> getAllBooks() {
        return mListBooks;
    }

    public void addNewBook(BookEntity book) {
        mBookDao.addBook(book);
    }

    public void updateBookDetails(BookEntity book) {
        mBookDao.modifyBook(book);
    }

    public void deleteBook(BookEntity book) {
        mBookDao.deleteBook(book);
    }

    public void deleteAllBooks() {
        mBookDao.deleteAll();
    }

    public BookEntity searchBook(int bookID) {
        return mBookDao.searchBook(bookID);
    }

    /**
     * MEMBERS TABLE
     */
    public LiveData<List<MemberEntity>> getAllMembers() {
        return mListMembers;
    }

    public void addNewMember(MemberEntity member) {
        mMemberDao.addMember(member);
    }

    public void updateMemberDetails(MemberEntity member) {
        mMemberDao.modifyMember(member);
    }

    public void deleteMember(MemberEntity member) {
        mMemberDao.deleteMember(member);
    }

    public void deleteAllMembers() {
        mMemberDao.deleteAll();
    }

    public MemberEntity searchMember(int memberID) {
        return mMemberDao.searchMember(memberID);
    }

    /**
     * TRANSACTIONS TABLE
     */
    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return mListTransactions;
    }

    public void loanBookToMember(TransactionEntity transaction) {
        mTransactionDao.addTransaction(transaction);
    }

    public void updateTransactionDetails(TransactionEntity transaction) {
        mTransactionDao.modifyTrasaction(transaction);
    }

    public void deleteTransaction(TransactionEntity transaction) {
        mTransactionDao.deleteTransaction(transaction);
    }

    public void deleteAllTransactions() {
        mTransactionDao.deleteAll();
    }

    public TransactionEntity searchTransaction(int transactionID) {
        return mTransactionDao.search(transactionID);
    }
}
