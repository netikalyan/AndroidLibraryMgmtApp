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
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.netikalyan.librarymanagement.R;
import com.netikalyan.librarymanagement.data.IEntityManagement;
import com.netikalyan.librarymanagement.data.ILibraryEntity;
import com.netikalyan.librarymanagement.data.LibraryException;

public class EntityItemActivity extends AppCompatActivity {

    public static final String SELECTED_TAB_TEXT = "SELECTED_TAB";
    public static final String DB_ITEM = "DB_ITEM";

    private static final int DELETE_CODE = 2;
    private static final int SAVE_CODE = 1;
    public static final int CANCEL_CODE = 0;

    private IEntityManagement mEntityManager;
    private IEntityManagement.Action mDBAction = IEntityManagement.Action.ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getString(R.string.app_name), "EntityItemActivity onCreate");
        setContentView(R.layout.activity_entity_item);
        String mTabText = getIntent().getStringExtra(SELECTED_TAB_TEXT);
        Fragment fragment;
        if (getString(R.string.tab_transaction_list).equals(mTabText)) {
            fragment = new TransactionFragment();
        } else if (getString(R.string.tab_member_list).equals(mTabText)) {
            fragment = new MemberFragment();
        } else {
            fragment = new BookFragment();
        }

        Parcelable parcelable = getIntent().getParcelableExtra(DB_ITEM);
        if (null != parcelable) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(DB_ITEM, parcelable);
            fragment.setArguments(bundle);
            mDBAction = IEntityManagement.Action.MODIFY;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment).commitNow();
        mEntityManager = (IEntityManagement) fragment;
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
                try {
                    ILibraryEntity entity = mEntityManager.get();
                    if (IEntityManagement.Action.ADD == mDBAction) {
                        mEntityManager.add(entity);
                    } else if (IEntityManagement.Action.MODIFY == mDBAction) {
                        mEntityManager.modify(entity);
                    }
                } catch (LibraryException e) {
                    Log.e(getString(R.string.app_name),
                            "Few of the details missing. Please fill relevant fields");
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
                setResult(SAVE_CODE);
                finish();
                break;
            case R.id.menu_cancel:
                setResult(CANCEL_CODE);
                finish();
                break;
            case R.id.menu_delete:
                try {
                    ILibraryEntity entity = mEntityManager.get();
                    mEntityManager.delete(entity);
                } catch (LibraryException e) {
                    Log.e(getString(R.string.app_name),
                            "Error in getting details. Please check all relevant fields");
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
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
