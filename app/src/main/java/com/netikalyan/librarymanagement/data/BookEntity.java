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

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Books")
public class BookEntity implements ILibraryEntity {
    @PrimaryKey
    @ColumnInfo(name = "BookID", typeAffinity = ColumnInfo.INTEGER)
    private int bookID;

    @ColumnInfo(name = "Title", typeAffinity = ColumnInfo.TEXT)
    private String title;

    @ColumnInfo(name = "Author", typeAffinity = ColumnInfo.TEXT)
    private String author;

    @ColumnInfo(name = "Price", typeAffinity = ColumnInfo.REAL)
    private float price;

    @ColumnInfo(name = "Available", typeAffinity = ColumnInfo.INTEGER)
    private int available;

    public BookEntity(Parcel source) {
        bookID = source.readInt();
        title = source.readString();
        author = source.readString();
        price = source.readFloat();
        available = source.readInt();
    }

    public BookEntity() {

    }

    @NonNull
    @Override
    public String toString() {
        return this.title;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new BookEntity(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new BookEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookID);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeFloat(this.price);
        dest.writeInt(this.available);
    }
}
