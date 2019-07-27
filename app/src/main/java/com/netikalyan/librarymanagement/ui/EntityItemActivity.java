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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.netikalyan.librarymanagement.OnFragmentInteractionListener.DBAction;
import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.BookEntity;
import com.netikalyan.librarymanagement.data.LibraryException;
import com.netikalyan.librarymanagement.data.MemberEntity;
import com.netikalyan.librarymanagement.data.TransactionEntity;

public class EntityItemActivity extends AppCompatActivity {

    public static final String SELECTED_TAB_TEXT = "SELECTED_TAB";
    public static final String DB_ACTION = "DB_ACTION";
    public static final String DB_ITEM = "DB_ITEM";
    private static final int DELETE_CODE = 2;
    private static final int SAVE_CODE = 1;
    public static final int CANCEL_CODE = 0;
    private Fragment mFragment;
    private String mTabText;
    private int mDBAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getString(R.string.app_name), "EntityItemActivity onCreate");
        setContentView(R.layout.activity_entity_item);
        mTabText = getIntent().getStringExtra(SELECTED_TAB_TEXT);
        mDBAction = getIntent()
                .getIntExtra(DB_ACTION, DBAction.ADD.ordinal());
        if (getString(R.string.tab_book_list).equals(mTabText)) {
            mFragment = new BookFragment();
            if (DBAction.MODIFY.ordinal() == mDBAction) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(DB_ITEM, getIntent().getParcelableExtra(DB_ITEM));
                mFragment.setArguments(bundle);
            }
        } else if (getString(R.string.tab_member_list).equals(mTabText)) {
            mFragment = new MemberFragment();
            if (DBAction.MODIFY.ordinal() == mDBAction) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(DB_ITEM, getIntent().getParcelableExtra(DB_ITEM));
                mFragment.setArguments(bundle);
            }
        } else if (getString(R.string.tab_transaction_list).equals(mTabText)) {
            mFragment = new TransactionFragment();
            if (DBAction.MODIFY.ordinal() == mDBAction) {
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
        new MenuInflater(getApplicationContext()).inflate(R.menu.actions, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (getString(R.string.tab_book_list).equals(mTabText)) {
                    try {
                        BookEntity bookEntity = ((BookFragment) mFragment).getBookDetails();
                        if (DBAction.ADD.ordinal() == mDBAction) {
                            ((BookFragment) mFragment).addBook(bookEntity);
                        } else if (DBAction.MODIFY.ordinal() == mDBAction) {
                            ((BookFragment) mFragment).modifyBook(bookEntity);
                        }
                    } catch (LibraryException e) {
                        Log.e(getString(R.string.app_name),
                                "Book details null. Please fill relevant fields");
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                } else if (getString(R.string.tab_member_list).equals(mTabText)) {
                    try {
                        MemberEntity memberEntity = ((MemberFragment) mFragment).getMemberDetails();
                        if (DBAction.ADD.ordinal() == mDBAction) {
                            ((MemberFragment) mFragment).addMember(memberEntity);
                        } else if (DBAction.MODIFY.ordinal() == mDBAction) {
                            ((MemberFragment) mFragment).modifyMember(memberEntity);
                        }
                    } catch (LibraryException e) {
                        Log.e(getString(R.string.app_name),
                                "Book details null. Please fill relevant fields");
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                } else if (getString(R.string.tab_transaction_list).equals(mTabText)) {
                    try {
                        TransactionEntity entity = ((TransactionFragment) mFragment)
                                .getTransactionDetails(DBAction.ADD.ordinal() == mDBAction);
                        if (DBAction.ADD.ordinal() == mDBAction) {
                            ((TransactionFragment) mFragment).loanBookToMember(entity);
                        } else if (DBAction.MODIFY.ordinal() == mDBAction) {
                            ((TransactionFragment) mFragment).returnBookToLibrary(entity);
                        }
                    } catch (LibraryException e) {
                        Log.e(getString(R.string.app_name),
                                "Transaction null. Please fill relevant fields");
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                setResult(SAVE_CODE);
                finish();
                break;
            case R.id.menu_cancel:
                setResult(CANCEL_CODE);
                finish();
                break;
            case R.id.menu_delete:
                if (getString(R.string.tab_book_list).equals(mTabText)) {
                    try {
                        ((BookFragment) mFragment)
                                .deleteBook(((BookFragment) mFragment).getBookDetails());
                    } catch (LibraryException e) {
                        Log.e(getString(R.string.app_name),
                                "Error in getting book details. Please check all relevant fields");
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                } else if (getString(R.string.tab_member_list).equals(mTabText)) {
                    try {
                        ((MemberFragment) mFragment).deleteMember(
                                ((MemberFragment) mFragment).getMemberDetails());
                    } catch (LibraryException e) {
                        Log.e(getString(R.string.app_name),
                                "Error in getting member details. Please check all relevant fields");
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                } else if (getString(R.string.tab_transaction_list).equals(mTabText)) {
                    try {
                        TransactionEntity entity =
                                ((TransactionFragment) mFragment).getTransactionDetails(false);
                        ((TransactionFragment) mFragment).deleteTransaction(entity);
                    } catch (LibraryException e) {
                        Log.e(getString(R.string.app_name),
                                "Error in getting transaction details. Please check all relevant fields");
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                setResult(DELETE_CODE);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(CANCEL_CODE);
    }
}
