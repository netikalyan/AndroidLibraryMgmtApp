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

package com.netikalyan.librarymanagement.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.netikalyan.librarymanagement.util.DateConverter;

import java.util.Date;

@Entity(tableName = "Transactions", foreignKeys = {
        @ForeignKey(entity = MemberEntity.class, childColumns = "MemberID", parentColumns = "MemberID"),
        @ForeignKey(entity = BookEntity.class, childColumns = "BookID", parentColumns = "BookID")})
@TypeConverters(DateConverter.class)
public class TransactionEntity implements ILibraryEntity {
    @PrimaryKey
    @ColumnInfo(name = "TransactionID", typeAffinity = ColumnInfo.INTEGER)
    private int transactionID;

    @ColumnInfo(name = "MemberID", typeAffinity = ColumnInfo.INTEGER, index = true)
    private int memberID;

    @Ignore
    private String memberName;

    @ColumnInfo(name = "BookID", typeAffinity = ColumnInfo.INTEGER, index = true)
    private int bookID;

    @Ignore
    private String bookTitle;

    @ColumnInfo(name = "LoanDate", typeAffinity = ColumnInfo.INTEGER)
    @TypeConverters(DateConverter.class)
    private Date dateOfLoan;

    @ColumnInfo(name = "ReturnDate", typeAffinity = ColumnInfo.INTEGER)
    @TypeConverters(DateConverter.class)
    private Date dateOfReturn;

    public TransactionEntity() {

    }

    public TransactionEntity(Parcel source) {
        transactionID = source.readInt();
        memberID = source.readInt();
        bookID = source.readInt();
        dateOfLoan = new Date(source.readLong());
        dateOfReturn = new Date(source.readLong());
    }

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

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setBookTitle(String title) {
        this.bookTitle = title;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public Date getDateOfLoan() {
        return dateOfLoan;
    }

    public void setDateOfLoan(@Nullable Date dateOfLoan) {
        this.dateOfLoan = dateOfLoan;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(@Nullable Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new TransactionEntity(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new TransactionEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.transactionID);
        dest.writeInt(this.memberID);
        dest.writeInt(this.bookID);
        dest.writeLong(this.dateOfLoan.getTime());
        if (null != this.dateOfReturn)
            dest.writeLong(this.dateOfReturn.getTime());
        else
            dest.writeLong(0);
    }
}
