package com.example.gaidamak.mathcatalog;

import android.app.Fragment;
import android.widget.TextView;

import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermCursor;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermSelection;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * Fragment where user can watch details of MathTerm
 * Layout and menu are inflated from XML
 */
@EFragment(R.layout.fragment_view_term)
@OptionsMenu(R.menu.fragment_view_term_menu)
public class ViewTermFragment extends Fragment {
    /**
     * Argument _ID of term, which is passed to fragment from activity via
     * {@link com.example.gaidamak.mathcatalog.ViewTermFragment_.FragmentBuilder_}
     */
    @FragmentArg
    Long termId;

    // Views inject by id.
    // Injection takes place ASAP please refer to AndroidAnnotation documentation
    @ViewById(R.id.formula_textview)
    TextView formulaTextView;
    @ViewById(R.id.description_textview)
    TextView descriptionTextView;
    @ViewById(R.id.tags_textview)
    TextView tagsTextView;

    /**
     * Invoking editing of current MathTerm
     * Handler of click on MenuItem.
     * Menu item is injected by id.
     * @see org.androidannotations.annotations.OptionsItem
     */
    @OptionsItem(R.id.action_edit)
    void edit() {
        ((FragmentManagingActivity) getActivity()).editMathTerm(termId);
    }

    /**
     * Initialize fields with content
     * @see org.androidannotations.annotations.AfterViews
     */
    @AfterViews
    void initViews() {
        MathTermSelection selection = new MathTermSelection();
        MathTermCursor cursor = selection.id(termId).query(getActivity().getContentResolver());
        if(cursor.getCount() != 1) {
            throw new RuntimeException("Unexpected number of items:" + cursor.getCount());
        }
        cursor.moveToFirst();

        getActivity().getActionBar().setTitle(cursor.getMathTerm());

        formulaTextView.setText(cursor.getMathFormula());
        descriptionTextView.setText(cursor.getDescription());
        tagsTextView.setText(cursor.getTags());
    }
}
