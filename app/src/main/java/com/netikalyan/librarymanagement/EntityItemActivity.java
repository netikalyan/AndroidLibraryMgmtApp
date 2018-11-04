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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class EntityItemActivity extends AppCompatActivity {

    public static final String SELECTED_TAB_TEXT = "SELECTED_TAB";
    public static final String DB_ACTION = "DB_ACTION";
    public static final String DB_ITEM = "DB_ITEM";
    public static final int SAVE_SUCCESS_CODE = 1;
    public static final int CANCEL_CODE = 0;
    private Fragment mFragment;
    private String mTabText;
    private int mDBAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_item);
        mTabText = getIntent().getStringExtra(SELECTED_TAB_TEXT);
        mDBAction = getIntent()
                .getIntExtra(DB_ACTION, OnFragmentInteractionListener.DBAction.ADD.ordinal());
        if (getString(R.string.tab_book_list).equals(mTabText)) {
            mFragment = new BookFragment();
            if (OnFragmentInteractionListener.DBAction.MODIFY.ordinal() == mDBAction) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(DB_ITEM, getIntent().getParcelableExtra(DB_ITEM));
                mFragment.setArguments(bundle);
            }
        } else if (getString(R.string.tab_member_list).equals(mTabText)) {
            mFragment = new MemberFragment();
            if (OnFragmentInteractionListener.DBAction.MODIFY.ordinal() == mDBAction) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(DB_ITEM, getIntent().getParcelableExtra(DB_ITEM));
                mFragment.setArguments(bundle);
            }
        } else if (getString(R.string.tab_transaction_list).equals(mTabText)) {
            mFragment = new TransactionFragment();
            if (OnFragmentInteractionListener.DBAction.MODIFY.ordinal() == mDBAction) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(DB_ITEM, getIntent().getParcelableExtra(DB_ITEM));
                mFragment.setArguments(bundle);
            }
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, mFragment).commitNow();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(R.string.menu_save).setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (getString(R.string.tab_book_list).equals(mTabText)) {
                            if (OnFragmentInteractionListener.DBAction.ADD.ordinal() == mDBAction) {
                                ((BookFragment) mFragment)
                                        .addBook(((BookFragment) mFragment).getBookDetails());
                            } else if (OnFragmentInteractionListener.DBAction.MODIFY.ordinal() ==
                                    mDBAction) {
                                ((BookFragment) mFragment)
                                        .modifyBook(((BookFragment) mFragment).getBookDetails());
                            }
                        } else if (getString(R.string.tab_member_list).equals(mTabText)) {
                            if (OnFragmentInteractionListener.DBAction.ADD.ordinal() == mDBAction) {
                                ((MemberFragment) mFragment)
                                        .addMember(((MemberFragment) mFragment).getMemberDetails());
                            } else if (OnFragmentInteractionListener.DBAction.MODIFY.ordinal() ==
                                    mDBAction) {
                                ((MemberFragment) mFragment)
                                        .modifyMember(
                                                ((MemberFragment) mFragment).getMemberDetails());
                            }
                        } else if (getString(R.string.tab_transaction_list).equals(mTabText)) {
                            if (OnFragmentInteractionListener.DBAction.ADD.ordinal() == mDBAction) {
                                ((TransactionFragment) mFragment).loanBookToMember(
                                        ((TransactionFragment) mFragment).getTransactionDetails());
                            } else if (OnFragmentInteractionListener.DBAction.MODIFY.ordinal() ==
                                    mDBAction) {
                                ((TransactionFragment) mFragment).returnBookToLibrary(
                                        ((TransactionFragment) mFragment).getTransactionDetails());
                            }
                        }
                        setResult(SAVE_SUCCESS_CODE);
                        finish();
                        return true;
                    }
                });
        menu.add(R.string.menu_cancel).setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        setResult(CANCEL_CODE);
                        finish();
                        return true;
                    }
                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
    }
}
