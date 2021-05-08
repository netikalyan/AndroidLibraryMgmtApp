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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.IMemberManagement;
import com.netikalyan.librarymanagement.data.LibraryException;
import com.netikalyan.librarymanagement.data.MemberEntity;
import com.netikalyan.librarymanagement.viewmodel.MemberViewModel;

import java.util.Objects;

public class MemberFragment extends Fragment implements IMemberManagement {

    private MemberViewModel mViewModel;
    private EditText editMemberID, editMemberName, editMemberInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_edit, container, false);
        editMemberID = rootView.findViewById(R.id.editMemberID);
        editMemberName = rootView.findViewById(R.id.editMemberName);
        editMemberInfo = rootView.findViewById(R.id.editMemberInfo);
        if (null != getArguments()) {
            set(Objects.requireNonNull(getArguments().getParcelable(EntityItemActivity.DB_ITEM)));
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MemberViewModel.class);
        mViewModel.getAllMembers().observe(this, memberEntities -> {
            // TODO: anything to do here ?
        });
    }

    @Override
    public void add(MemberEntity member) {
        mViewModel.addNewMember(member);
    }

    @Override
    public MemberEntity search(int memberID) {
        return mViewModel.searchMember(memberID);
    }

    @Override
    public void modify(MemberEntity member) {
        mViewModel.updateMember(member);
    }

    @Override
    public void delete(MemberEntity member) {
        mViewModel.deleteMember(member);
    }

    @NonNull
    public MemberEntity get() throws LibraryException {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberID(getMemberID());
        memberEntity.setName(getMemberName());
        memberEntity.setAddlInfo(getMemberInfo());
        return memberEntity;
    }

    @Override
    public void set(@NonNull MemberEntity member) {
        editMemberID.setText(String.valueOf(member.getMemberID()));
        editMemberName.setText(member.getName());
        editMemberInfo.setText(member.getAddlInfo());
    }

    private int getMemberID() throws LibraryException {
        String memberID = editMemberID.getText().toString();
        if (!memberID.isEmpty())
            return Integer.parseInt(memberID);
        throw new LibraryException(LibraryException.Constants.MEMBER_ID_MISSING);
    }

    @NonNull
    private String getMemberName() throws LibraryException {
        String name = editMemberName.getText().toString();
        if (!name.isEmpty())
            return name;
        throw new LibraryException(LibraryException.Constants.MEMBER_NAME_MISSING);
    }

    @Nullable
    private String getMemberInfo() {
        String info = editMemberInfo.getText().toString();
        if (!info.isEmpty())
            return info;
        else
            return null;
    }
}
