package com.example.gaidamak.mathcatalog;

import android.app.Fragment;
import android.widget.EditText;

import com.commonsware.cwac.richedit.RichEditText;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermContentValues;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermCursor;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermSelection;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.kpbird.chipsedittextlibrary.ChipsMultiAutoCompleteTextview;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * Fragment where user can edit details of MathTerm
 * Layout and menu are inflated from XML
 */
@EFragment(R.layout.fragment_edit_term)
@OptionsMenu(R.menu.fragment_edit_term_menu)
public class EditTermFragment extends Fragment {
    /**
     * -1 is a flag that indicates that this is new Item.
     * If it is not true this variable will be injected with correct _ID
     */
    @FragmentArg
    Long termId = -1l;

    // FloatLabel is a lib component, so cute PopUp is show as hint
    @ViewById(R.id.title_edittext)
    FloatLabel titleTextView;
    @ViewById(R.id.url_edittext)
    FloatLabel urlTextView;
    @ViewById(R.id.formula_edittext)
    FloatLabel formulaTextView;

    @ViewById(R.id.description_edittext)
    EditText descriptionTextView;
    // FloatLabel works only with one line
//    FloatLabel descriptionTextView;
    @ViewById(R.id.tags_edittext)
    ChipsMultiAutoCompleteTextview tagsTextView;

    /**
     * Handling click on save options menu.
     * Writing all data to DB and closing fragment
     */
    @OptionsItem(R.id.action_save)
    void save() {
        MathTermContentValues contentValues = new MathTermContentValues();
        contentValues.putMathTerm(titleTextView.getEditText().getText().toString());
        contentValues.putUrl(urlTextView.getEditText().getText().toString());
        contentValues.putMathFormula(formulaTextView.getEditText().getText().toString());
        contentValues.putDescription(descriptionTextView.getText().toString());
        contentValues.putTags(tagsTextView.getText().toString());
        if (termId != -1) {
            contentValues.update(getActivity().getContentResolver(), new MathTermSelection().id(termId));
        } else {
            contentValues.insert(getActivity().getContentResolver());
        }
        cancel();
    }

    /**
     * Handling click and asking activity to close this fragment
     */
    @OptionsItem(R.id.action_cancel)
    void cancel() {
        ((FragmentManagingActivity) getActivity()).cancel();
    }


    /**
     * Initialize fields with content
     *
     * @see org.androidannotations.annotations.AfterViews
     */
    @AfterViews
    void initViews() {
        if (termId == -1) {
            getActivity().getActionBar().setTitle("New term");
            return;
        }
        MathTermSelection selection = new MathTermSelection();
        MathTermCursor cursor = selection.id(termId).query(getActivity().getContentResolver());
        if (cursor.getCount() != 1) {
            throw new RuntimeException("Unexpected number of items:" + cursor.getCount());
        }
        cursor.moveToFirst();

        getActivity().getActionBar().setTitle(cursor.getMathTerm());

        titleTextView.getEditText().setText(cursor.getMathTerm());
        formulaTextView.getEditText().setText(cursor.getMathFormula());
        urlTextView.getEditText().setText(cursor.getUrl());

        descriptionTextView.setText(cursor.getDescription());
        tagsTextView.setText(cursor.getTags());

        RichEditText descriptionEditor = (RichEditText) descriptionTextView;
    }
}
