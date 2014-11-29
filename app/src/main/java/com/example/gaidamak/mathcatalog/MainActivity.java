package com.example.gaidamak.mathcatalog;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


public class MainActivity extends Activity implements FragmentManagingActivity {

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
                .replace(R.id.container, ViewTermFragment_.builder().termId(id).build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void editMathTerm(long id) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, EditTermFragment_.builder().termId(id).build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addNewTerm() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, EditTermFragment_.builder().build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        getFragmentManager().popBackStack();
    }
}
