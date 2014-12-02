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
 * Fragment with list of all available Math terms.
 * Implements {@link android.app.LoaderManager.LoaderCallbacks}
 * so list can be updated instantly after DB change.
 * Layout and menu are inflated from XML
 */
@EFragment(R.layout.fragment_term_list)
@OptionsMenu(R.menu.fragment_term_list_menu)
public class MathListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SearchView.OnQueryTextListener{
    private static final String TAG = "MathListFragment";
    private static final String FILTER = "filter";

    /**
     * Injected listView where all math terms are displayed as listItems
     */
    @ViewById(android.R.id.list)
    EnhancedListView listView;

    private SimpleCursorAdapter mAdapter;
    private FragmentManagingActivity activity;

    /**
     * Wrapper for adapter. This is the thing to make listItems animated.
     */
    private AnimationAdapter mAnimAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getActionBar().setTitle(getActivity().getResources().getString(R.string.app_name));

        activity = (FragmentManagingActivity) getActivity();
        // Projection
        String[] from = {MathTermColumns.MATH_TERM, MathTermColumns.MATH_FORMULA};
        int[] to = { R.id.term_name, R.id.term_formula };
        // Flags == 0 because we are using LoaderCallbacks
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.custom_two_line_list_item,
                null, from, to, 0);

        // Wrapping adapter
        mAnimAdapter = new SwingRightInAnimationAdapter(mAdapter);
        mAnimAdapter.setAbsListView(listView);
        listView.setAdapter(mAnimAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.v(TAG, "item " + id + " clicked.");
                activity.viewMathTerm(id);
            }
        });
        // Deletion happens here
        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, int position) {
                long id = mAdapter.getItemId(position);
                // We need this, so items won't flicker after deletion
                SwipeToDeleteCursorWrapper cursorWrapper =
                        new SwipeToDeleteCursorWrapper(mAdapter.getCursor(), position);
                mAdapter.swapCursor(cursorWrapper);
                new MathTermSelection().id(id).delete(getActivity().getContentResolver());
                return null;
            }
        });
        listView.enableSwipeToDismiss();

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Initializing search in actionBar
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);
    }

    /**
     * Handling click on menu button, and delegating call
     * to {@link com.example.gaidamak.mathcatalog.MainActivity}
     */
    @OptionsItem
    void addItem() {
        Log.v(TAG, "add item");
        activity.addNewTerm();
    }

    @OptionsItem(R.id.aboutItem)
    void showAboutScreen() {
        activity.showSplashScreen(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String filter = null;
        if (bundle != null)
            filter = bundle.getString(FILTER);

        CursorLoader cl = new CursorLoader(getActivity(),
                MathTermColumns.CONTENT_URI,
                null,   // Projection
                filter, // Selection
                null,   // Selection args
                null);  // Sort order
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

    /**
     * Not interested in event
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    /**
     * Forming selection and requesting new {@link android.database.Cursor}.
     * Search happens here.
     * @param s
     * @return
     */
    @Override
    public boolean onQueryTextChange(String s) {
        Bundle bundle = new Bundle();
        // SQL has problems with no ASCII letters
        // So this is a hack to perform case independent search
        String filter = MathTermColumns.MATH_TERM_LOWERCASE + " LIKE '%" + s.toLowerCase() + "%'";
        bundle.putString(FILTER, filter);
        getLoaderManager().restartLoader(0, bundle, this);
        return true;
    }
}
