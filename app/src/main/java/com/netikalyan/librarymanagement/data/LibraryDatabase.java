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

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.netikalyan.librarymanagement.util.DateConverter;

@Database(entities = {BookEntity.class, MemberEntity.class,
        TransactionEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class LibraryDatabase extends RoomDatabase {
    public abstract BookDao bookDao();

    public abstract MemberDao memberDao();

    public abstract TransactionDao transactionDao();

    private static volatile LibraryDatabase INSTANCE;

    // TODO: Added allowMainThreadQueries to avoid crash. Have to remove it later. Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
    public static LibraryDatabase getDatabase(Application application) {
        if (null == INSTANCE) {
            synchronized (LibraryDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = Room.databaseBuilder(application.getApplicationContext(),
                            LibraryDatabase.class, "LibraryDB").allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            }).build();
                }
            }
        }
        return INSTANCE;
    }
}
