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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Transactions", foreignKeys = {@ForeignKey(entity = MemberEntity.class, childColumns = "MemberID", parentColumns = "MemberID", onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION), @ForeignKey(entity = BookEntity.class, childColumns = "BookID", parentColumns = "BookID")})
@TypeConverters(DateConverter.class)
public class TransactionEntity {
    @PrimaryKey
    @ColumnInfo(name = "TransactionID", typeAffinity = ColumnInfo.INTEGER)
    private int transactionID;

    @ColumnInfo(name = "MemberID", typeAffinity = ColumnInfo.INTEGER, index = true)
    private int memberID;

    @ColumnInfo(name = "BookID", typeAffinity = ColumnInfo.INTEGER, index = true)
    private int bookID;

    @ColumnInfo(name = "LoanDate", typeAffinity = ColumnInfo.INTEGER)
    @TypeConverters(DateConverter.class)
    private Date dateOfLoan;

    @ColumnInfo(name = "ReturnDate", typeAffinity = ColumnInfo.INTEGER)
    @TypeConverters(DateConverter.class)
    private Date dateOfReturn;

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Date getDateOfLoan() {
        return dateOfLoan;
    }

    public void setDateOfLoan(Date dateOfLoan) {
        this.dateOfLoan = dateOfLoan;
    }

    public Date getDateOfReturn() {
        return dateOfLoan;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }
}
