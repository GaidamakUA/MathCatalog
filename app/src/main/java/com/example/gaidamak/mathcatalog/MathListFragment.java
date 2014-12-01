package com.example.gaidamak.mathcatalog;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermColumns;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermSelection;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import de.timroes.android.listview.EnhancedListView;

/**
 * Fragment with list of all available Math terms
 * Layout and menu are inflated from XML
 */
@EFragment(R.layout.fragment_main)
@OptionsMenu(R.menu.add_item)
public class MathListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SearchView.OnQueryTextListener{
    @ViewById(android.R.id.list)
    EnhancedListView listView;

    private static final String TAG = "MathListFragment";
    private static final String FILTER = "filter";

    SimpleCursorAdapter mAdapter;
    FragmentManagingActivity activity;

    private AnimationAdapter mAnimAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getActionBar().setTitle(getActivity().getResources().getString(R.string.app_name));

        activity = (FragmentManagingActivity) getActivity();
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.custom_two_line_list_item,
                null,
                new String[] {MathTermColumns.MATH_TERM, MathTermColumns.MATH_FORMULA},
                new int[] { R.id.term_name, R.id.term_formula }, 0);

        mAnimAdapter = new SwingRightInAnimationAdapter(mAdapter);
        mAnimAdapter.setAbsListView(listView);
        listView.setAdapter(mAnimAdapter);

//        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.v(TAG, "item " + id + " clicked.");
                activity.viewMathTerm(id);
            }
        });
        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, int position) {
                long id = mAdapter.getItemId(position);
                SwipeToDeleteCursorWrapper cursorWrapper =
                        new SwipeToDeleteCursorWrapper(mAdapter.getCursor(), position);
                mAdapter.swapCursor(cursorWrapper);
                // I have no idea why -1. Probably library bug
                new MathTermSelection().id(id).delete(getActivity().getContentResolver());
                return null;
            }
        });
        listView.enableSwipeToDismiss();

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);
    }

    @OptionsItem
    void addItem() {
        Log.v(TAG, "add item");
        activity.addNewTerm();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String filter = null;
        if (bundle != null)
            filter = bundle.getString(FILTER);
        CursorLoader cl = new CursorLoader(getActivity(), MathTermColumns.CONTENT_URI, null, filter,
                null, null);
        return cl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Bundle bundle = new Bundle();
        String filter = MathTermColumns.MATH_TERM + " LIKE '%" + s + "%'";
        bundle.putString(FILTER, filter);
        getLoaderManager().restartLoader(0, bundle, this);
        return true;
    }
}
