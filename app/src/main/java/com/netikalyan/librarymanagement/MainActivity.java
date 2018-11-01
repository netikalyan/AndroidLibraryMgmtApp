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
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private BookFragment mBookFragment;
    private MemberFragment mMemberFragment;
    private TransactionFragment mTransactionFragment;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.navigationContainer, new NavigationFragment()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(R.id.optionsContainer, new OptionsFragment()).commitNow();
        }

        mBookFragment = BookFragment.newInstance();
        mMemberFragment = MemberFragment.newInstance();
        mTransactionFragment = TransactionFragment.newInstance();

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(getString(R.string.tab_book))) {
                    getSupportFragmentManager().beginTransaction().add(R.id.container, mBookFragment).commitNow();
                } else if (tab.getText().equals(getString(R.string.tab_member))) {
                    getSupportFragmentManager().beginTransaction().add(R.id.container, mMemberFragment).commitNow();
                } else if (tab.getText().equals(getString(R.string.tab_transaction))) {
                    getSupportFragmentManager().beginTransaction().add(R.id.container, mTransactionFragment).commitNow();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getText().equals(getString(R.string.tab_book))) {
                    getSupportFragmentManager().beginTransaction().remove(mBookFragment).commitNow();
                } else if (tab.getText().equals(getString(R.string.tab_member))) {
                    getSupportFragmentManager().beginTransaction().remove(mMemberFragment).commitNow();
                } else if (tab.getText().equals(getString(R.string.tab_transaction))) {
                    getSupportFragmentManager().beginTransaction().remove(mTransactionFragment).commitNow();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onFragmentInteraction(DBAction action) {
        int position = mTabLayout.getSelectedTabPosition();
        TabLayout.Tab currentTab = mTabLayout.getTabAt(position);
        if (currentTab.getText().equals(getString(R.string.tab_book))) {
            switch (action) {
                case ADD:
                    break;
                case DELETE:
                    break;
                case MODIFY:
                    break;
                case SEARCH:
                    break;
            }
        } else if (currentTab.getText().equals(getString(R.string.tab_member))) {
            switch (action) {
                case ADD:
                    break;
                case DELETE:
                    break;
                case MODIFY:
                    break;
                case SEARCH:
                    break;
            }
        } else if (currentTab.getText().equals(getString(R.string.tab_transaction))) {
            switch (action) {
                case ADD:
                    break;
                case DELETE:
                    break;
                case MODIFY:
                    break;
                case SEARCH:
                    break;
            }
        }
    }
}
