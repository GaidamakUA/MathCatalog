package com.example.gaidamak.mathcatalog;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermColumns;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermContentValues;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermSelection;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EFragment(R.layout.fragment_main)
@OptionsMenu(R.menu.add_item)
public class MathListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MathListFragment";

    SimpleCursorAdapter mAdapter;
    FragmentManagingActivity activity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (FragmentManagingActivity) getActivity();
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.custom_two_line_list_item,
                null,
                new String[] {MathTermColumns.MATH_TERM, MathTermColumns.MATH_FORMULA},
                new int[] { R.id.term_name, R.id.term_formula }, 0);
        setListAdapter(mAdapter);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                new MathTermSelection().id(id).delete(getActivity().getContentResolver());
                return true;
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @OptionsItem
    void addItem() {
        Log.v(TAG, "add item");
        activity.addNewTerm();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
        Log.v(TAG, "item " + id + " clicked.");
        activity.viewMathTerm(id);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader cl = new CursorLoader(getActivity(), MathTermColumns.CONTENT_URI, null, null,
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
}
