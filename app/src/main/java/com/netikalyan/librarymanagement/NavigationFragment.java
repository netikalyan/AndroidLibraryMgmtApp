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

public class NavigationFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private Button btnFirst, btnPrevious, btnNext, btnLast;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.db_navigation_fragment, container, false);
        btnFirst = rootView.findViewById(R.id.btnFirst);
        btnFirst.setOnClickListener(this);
        btnPrevious = rootView.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(this);
        btnNext = rootView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        btnLast = rootView.findViewById(R.id.btnLast);
        btnLast.setOnClickListener(this);
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
            case R.id.btnFirst:
                mListener.onFragmentInteraction(OnFragmentInteractionListener.DBAction.FIRST);
                break;
            case R.id.btnPrevious:
                mListener.onFragmentInteraction(OnFragmentInteractionListener.DBAction.PREVIOUS);
                break;
            case R.id.btnNext:
                mListener.onFragmentInteraction(OnFragmentInteractionListener.DBAction.NEXT);
                break;
            case R.id.btnLast:
                mListener.onFragmentInteraction(OnFragmentInteractionListener.DBAction.LAST);
                break;
            default:
                break;
        }
    }
}
