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
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class MemberViewModel extends AndroidViewModel {
    private LiveData<List<MemberEntity>> mMemberList;
    private LibraryRepository mRepository;

    public MemberViewModel(@NonNull Application application) {
        super(application);
        Log.e(application.getString(R.string.app_name), "BookViewModel");
        mRepository = new LibraryRepository(application);
        mMemberList = mRepository.getAllMembers();
    }

    public LiveData<List<MemberEntity>> getAllMembers() {
        return mMemberList;
    }

    public void addNewMember(MemberEntity member) {
        mRepository.addNewMember(member);
    }

    public void updateMember(MemberEntity member) {
        mRepository.updateMemberDetails(member);
    }

    public void deleteMember(MemberEntity member) {
        mRepository.deleteMember(member);
    }

    public void deleteAllMembers() {
        mRepository.deleteAllBooks();
    }

    public MemberEntity searchMember(int memberID) {
        return mRepository.searchMember(memberID);
    }

    public MemberEntity[] searchMemberByName(String memberName) {
        return mRepository.searchMemberByName(memberName);
    }
}
