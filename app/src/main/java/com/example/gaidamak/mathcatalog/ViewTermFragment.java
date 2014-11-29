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

@EFragment(R.layout.fragment_view_term)
@OptionsMenu(R.menu.fragment_view_term_menu)
public class ViewTermFragment extends Fragment {
    @FragmentArg
    Long termId;

    @ViewById(R.id.description_textview)
    TextView descriptionTextView;
    @ViewById(R.id.tags_textview)
    TextView tagsTextView;

    @OptionsItem(R.id.action_edit)
    void edit() {
        ((FragmentManagingActivity) getActivity()).editMathTerm(termId);
    }

    @AfterViews
    void initViews() {
        MathTermSelection selection = new MathTermSelection();
        MathTermCursor cursor = selection.id(termId).query(getActivity().getContentResolver());
        assert cursor.getCount() == 1;
        cursor.moveToFirst();

        getActivity().getActionBar().setTitle(cursor.getMathTerm());

        descriptionTextView.setText(cursor.getDescription());
        tagsTextView.setText(cursor.getTags());
    }
}
