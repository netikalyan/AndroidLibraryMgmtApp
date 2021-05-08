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

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.ILibraryEntity;
import com.netikalyan.librarymanagement.data.OnListFragmentInteractionListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener {
    private BookListFragment mBookListFragment;
    private MemberListFragment mMemberListFragment;
    private TransactionListFragment mTransactionListFragment;
    private String mTabText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mBookListFragment = new BookListFragment();
        mMemberListFragment = new MemberListFragment();
        mTransactionListFragment = new TransactionListFragment();

        FloatingActionButton fabNew = findViewById(R.id.fabAddNew);
        fabNew.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EntityItemActivity.class);
            intent.putExtra(EntityItemActivity.SELECTED_TAB_TEXT, mTabText);
            startActivity(intent);
        });

        TabLayout mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabText = Objects.requireNonNull(tab.getText()).toString();
                if (getString(R.string.tab_book_list).equals(mTabText)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mBookListFragment).commitNow();
                } else if (getString(R.string.tab_member_list).equals(mTabText)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mMemberListFragment).commitNow();
                } else if (getString(R.string.tab_transaction_list).equals(mTabText)) {
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
    public void onListFragmentInteraction(ILibraryEntity item) {
        Intent intent = new Intent(getApplicationContext(), EntityItemActivity.class);
        intent.putExtra(EntityItemActivity.SELECTED_TAB_TEXT, mTabText);
        intent.putExtra(EntityItemActivity.DB_ITEM, item);
        startActivity(intent);
    }
}