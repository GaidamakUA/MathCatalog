package com.example.gaidamak.mathcatalog;

import android.app.Fragment;
import android.widget.TextView;

import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermContentValues;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermCursor;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermSelection;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_edit_term)
@OptionsMenu(R.menu.fragment_edit_term_menu)
public class EditTermFragment extends Fragment {
    @FragmentArg
    Long termId = -1l;

    @ViewById(R.id.title_edittext)
    TextView titleTextView;
    @ViewById(R.id.formula_edittext)
    TextView formulaTextView;

    @ViewById(R.id.description_edittext)
    TextView descriptionTextView;
    @ViewById(R.id.tags_edittext)
    TextView tagsTextView;

    @OptionsItem(R.id.action_save)
    void save() {
        MathTermContentValues contentValues = new MathTermContentValues();
        contentValues.putMathTerm(titleTextView.getText().toString());
        contentValues.putMathFormula(formulaTextView.getText().toString());
        contentValues.putDescription(descriptionTextView.getText().toString());
        contentValues.putTags(tagsTextView.getText().toString());
        if (termId != -1) {
            contentValues.update(getActivity().getContentResolver(), new MathTermSelection().id(termId));
        } else {
            contentValues.insert(getActivity().getContentResolver());
        }
        cancel();
    }

    @OptionsItem(R.id.action_cancel)
    void cancel() {
        ((FragmentManagingActivity) getActivity()).cancel();
    }

    @AfterViews
    void initViews() {
        if (termId == -1) {
            getActivity().getActionBar().setTitle("New term");
            return;
        }
        MathTermSelection selection = new MathTermSelection();
        MathTermCursor cursor = selection.id(termId).query(getActivity().getContentResolver());
        assert cursor.getCount() == 1;
        cursor.moveToFirst();

        getActivity().getActionBar().setTitle(cursor.getMathTerm());

        titleTextView.setText(cursor.getMathTerm());
        formulaTextView.setText(cursor.getMathFormula());

        descriptionTextView.setText(cursor.getDescription());
        tagsTextView.setText(cursor.getTags());
    }
}
