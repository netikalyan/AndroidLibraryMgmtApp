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

import java.util.List;

public class MemberFragment extends Fragment {

    private MemberViewModel mViewModel;
    private EditText editMemberID, editMemberName, editMemberInfo;

    public static MemberFragment newInstance(MemberEntity entity) {
        MemberFragment fragment = new MemberFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ENTITY", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.member_fragment, container, false);
        editMemberID = rootView.findViewById(R.id.editMemberID);
        editMemberName = rootView.findViewById(R.id.editMemberName);
        editMemberInfo = rootView.findViewById(R.id.editMemberInfo);
        if (null != getArguments()) {
            setMember((MemberEntity) getArguments().getParcelable(EntityItemActivity.DB_ITEM));
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        mViewModel.getAllMembers().observe(this, new Observer<List<MemberEntity>>() {
            @Override
            public void onChanged(@Nullable List<MemberEntity> memberEntities) {
                // TODO: anything to do here ?
            }
        });
    }

    public void addMember(MemberEntity member) {
        mViewModel.addNewMember(member);
    }

    public MemberEntity searchMember(int memberID) {
        return mViewModel.searchMember(memberID);
    }

    public void modifyMember(MemberEntity member) {
        mViewModel.updateMember(member);
    }

    public void deleteMember(MemberEntity member) {
        mViewModel.deleteMember(member);
    }

    @NonNull
    public MemberEntity getMemberDetails() throws LibraryException {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberID(getMemberID());
        memberEntity.setName(getMemberName());
        memberEntity.setAddlInfo(getMemberInfo());
        return memberEntity;
    }

    public int getMemberID() throws LibraryException {
        String memberID = editMemberID.getText().toString();
        if (!memberID.isEmpty())
            return Integer.parseInt(memberID);
        throw new LibraryException(LibraryException.Constants.MEMBER_ID_MISSING);
    }

    @NonNull
    public String getMemberName() throws LibraryException {
        String name = editMemberName.getText().toString();
        if (!name.isEmpty())
            return name;
        throw new LibraryException(LibraryException.Constants.MEMBER_NAME_MISSING);
    }

    @NonNull
    public String getMemberInfo() throws LibraryException {
        String info = editMemberInfo.getText().toString();
        if (!info.isEmpty())
            return info;
        throw new LibraryException(LibraryException.Constants.MEMBER_INFO_MISSING);
    }

    private void setMember(@NonNull MemberEntity member) {
        editMemberID.setText(String.valueOf(member.getMemberID()));
        editMemberName.setText(member.getName());
        editMemberInfo.setText(member.getAddlInfo());
    }
}
