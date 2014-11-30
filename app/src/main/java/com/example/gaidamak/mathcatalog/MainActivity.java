package com.example.gaidamak.mathcatalog;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends Activity implements FragmentManagingActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowHomeEnabled(false);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MathListFragment_())
                    .commit();
        }
    }

    @Override
    public void viewMathTerm(long id) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_right)
                .replace(R.id.container, ViewTermFragment_.builder().termId(id).build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void editMathTerm(long id) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_right)
                .replace(R.id.container, EditTermFragment_.builder().termId(id).build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addNewTerm() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_right)
                .replace(R.id.container, EditTermFragment_.builder().build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        getFragmentManager().popBackStack();
    }
}
