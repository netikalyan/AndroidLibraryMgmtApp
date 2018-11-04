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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements OnFragmentInteractionListener, OnListFragmentInteractionListener {
    private BookListFragment mBookListFragment;
    private MemberListFragment mMemberListFragment;
    private TransactionListFragment mTransactionListFragment;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mBookListFragment = new BookListFragment();
        mMemberListFragment = new MemberListFragment();
        mTransactionListFragment = new TransactionListFragment();

        FloatingActionButton fabNew = findViewById(R.id.fabAddNew);
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout.Tab tab = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition());
                Intent intent = new Intent(getApplicationContext(), EntityItemActivity.class);
                intent.putExtra(EntityItemActivity.SELECTED_TAB_TEXT, tab.getText().toString());
                startActivityForResult(intent, mTabLayout.getSelectedTabPosition());
            }
        });

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (getString(R.string.tab_book_list).equals(tab.getText().toString())) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mBookListFragment).commitNow();
                } else if (getString(R.string.tab_member_list).equals(tab.getText().toString())) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mMemberListFragment).commitNow();
                } else if (getString(R.string.tab_transaction_list)
                        .equals(tab.getText().toString())) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mTransactionListFragment).commitNow();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mBookListFragment)
                .commitNow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (EntityItemActivity.SAVE_SUCCESS_CODE == resultCode) {
            switch (requestCode) {
                case 0: // BookFragment
                    break;
                case 1: //MemberFragment
                    break;
                case 2: //TransactionFragment
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onFragmentInteraction(DBAction action) {
    }

    @Override
    public void onListFragmentInteraction(ILibraryEntity item) {
        Intent intent = new Intent(getApplicationContext(), EntityItemActivity.class);
        if (item instanceof BookEntity) {
            intent.putExtra(EntityItemActivity.SELECTED_TAB_TEXT, getString(R.string.tab_book));
            intent.putExtra(EntityItemActivity.DB_ACTION, DBAction.MODIFY.ordinal());
        } else if (item instanceof MemberEntity) {
            intent.putExtra(EntityItemActivity.SELECTED_TAB_TEXT, getString(R.string.tab_member));
            intent.putExtra(EntityItemActivity.DB_ACTION, DBAction.MODIFY.ordinal());
        } else if (item instanceof TransactionEntity) {
            intent.putExtra(EntityItemActivity.SELECTED_TAB_TEXT,
                    getString(R.string.tab_transaction));
            intent.putExtra(EntityItemActivity.DB_ACTION, DBAction.MODIFY.ordinal());
        }
        intent.putExtra(EntityItemActivity.DB_ITEM, item);
        startActivityForResult(intent, mTabLayout.getSelectedTabPosition());
    }
}