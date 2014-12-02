//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.1.
//


package com.example.gaidamak.mathcatalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.gaidamak.mathcatalog.R.layout;
import com.iangclifton.android.floatlabel.FloatLabel;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class EditTermFragment_
    extends com.example.gaidamak.mathcatalog.EditTermFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;
    public final static String TERM_ID_ARG = "termId";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_edit_term, container, false);
        }
        return contentView_;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        injectFragmentArguments_();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static EditTermFragment_.FragmentBuilder_ builder() {
        return new EditTermFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        tagsTextView = ((FloatLabel) hasViews.findViewById(com.example.gaidamak.mathcatalog.R.id.tags_edittext));
        titleTextView = ((FloatLabel) hasViews.findViewById(com.example.gaidamak.mathcatalog.R.id.title_edittext));
        descriptionTextView = ((EditText) hasViews.findViewById(com.example.gaidamak.mathcatalog.R.id.description_edittext));
        formulaTextView = ((FloatLabel) hasViews.findViewById(com.example.gaidamak.mathcatalog.R.id.formula_edittext));
        initViews();
    }

    private void injectFragmentArguments_() {
        Bundle args_ = getArguments();
        if (args_!= null) {
            if (args_.containsKey(TERM_ID_ARG)) {
                termId = ((Long) args_.getSerializable(TERM_ID_ARG));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(com.example.gaidamak.mathcatalog.R.menu.fragment_edit_term_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = super.onOptionsItemSelected(item);
        if (handled) {
            return true;
        }
        int itemId_ = item.getItemId();
        if (itemId_ == com.example.gaidamak.mathcatalog.R.id.action_save) {
            save();
            return true;
        }
        if (itemId_ == com.example.gaidamak.mathcatalog.R.id.action_cancel) {
            cancel();
            return true;
        }
        return false;
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<EditTermFragment_.FragmentBuilder_, com.example.gaidamak.mathcatalog.EditTermFragment>
    {


        @Override
        public com.example.gaidamak.mathcatalog.EditTermFragment build() {
            EditTermFragment_ fragment_ = new EditTermFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }

        public EditTermFragment_.FragmentBuilder_ termId(Long termId) {
            args.putSerializable(TERM_ID_ARG, termId);
            return this;
        }

    }

}
