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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class OptionsFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private Button btnAdd, btnSearch, btnModify, btnDelete;

    public static OptionsFragment newInstance() {
        return new OptionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.db_actions_fragment, container, false);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnSearch = rootView.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        btnModify = rootView.findViewById(R.id.btnUpdate);
        btnModify.setOnClickListener(this);
        btnDelete = rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                mListener.onFragmentInteraction(OnFragmentInteractionListener.DBAction.ADD);
                break;
            case R.id.btnSearch:
                mListener.onFragmentInteraction(OnFragmentInteractionListener.DBAction.SEARCH);
                break;
            case R.id.btnUpdate:
                mListener.onFragmentInteraction(OnFragmentInteractionListener.DBAction.MODIFY);
                break;
            case R.id.btnDelete:
                mListener.onFragmentInteraction(OnFragmentInteractionListener.DBAction.DELETE);
                break;
            default:
                break;
        }
    }
}
