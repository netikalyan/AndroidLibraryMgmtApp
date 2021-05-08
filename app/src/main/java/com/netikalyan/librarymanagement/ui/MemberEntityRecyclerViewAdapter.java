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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.MemberEntity;
import com.netikalyan.librarymanagement.data.OnListFragmentInteractionListener;

import java.util.List;

public class MemberEntityRecyclerViewAdapter
        extends RecyclerView.Adapter<MemberEntityRecyclerViewAdapter.ViewHolder> {

    private List<MemberEntity> mMemberList;
    private final OnListFragmentInteractionListener mListener;

    MemberEntityRecyclerViewAdapter(List<MemberEntity> members,
                                    OnListFragmentInteractionListener listener) {
        mMemberList = members;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_memberentity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mMemberList.get(position);
        holder.mMemberIdView.setText(String.valueOf(mMemberList.get(position).getMemberID()));
        holder.mNameView.setText(mMemberList.get(position).getName());
        String addlInfo = mMemberList.get(position).getAddlInfo();
        if (null != addlInfo) {
            holder.mOtherInfoView.setText(addlInfo);
        }

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mMemberList)
            return 0;
        else
            return mMemberList.size();
    }

    void setMemberList(List<MemberEntity> memberEntities) {
        mMemberList = memberEntities;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mMemberIdView;
        final TextView mNameView;
        final TextView mOtherInfoView;
        MemberEntity mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mMemberIdView = view.findViewById(R.id.textMemberID);
            mNameView = view.findViewById(R.id.textMemberName);
            mOtherInfoView = view.findViewById(R.id.textMemberOtherInfo);
        }
    }
}
