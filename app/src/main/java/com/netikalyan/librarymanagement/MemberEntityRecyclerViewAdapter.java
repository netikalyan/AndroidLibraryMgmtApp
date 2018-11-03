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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netikalyan.librarymanagement.MemberListFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MemberEntityRecyclerViewAdapter extends RecyclerView.Adapter<MemberEntityRecyclerViewAdapter.ViewHolder> {

    private List<MemberEntity> mMemberList;
    private final OnListFragmentInteractionListener mListener;

    MemberEntityRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
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
        holder.mOtherInfoView.setText(mMemberList.get(position).getAddlInfo());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
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
            mMemberIdView = view.findViewById(R.id.textBookID);
            mNameView = view.findViewById(R.id.textBookTitle);
            mOtherInfoView = view.findViewById(R.id.textBookAuthor);
        }
    }
}
