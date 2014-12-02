package com.example.gaidamak.mathcatalog;

import android.app.Fragment;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.concurrent.TimeUnit;

@EFragment(R.layout.fragment_splash_screen)
public class SplashScreenFragment extends Fragment {
    @Pref
    SharePreferences_ pref;

    private static final String TAG = "SplashScreenFragment";

    @FragmentArg
    boolean explicitCall = false;
    boolean shouldAutoclose = true;
    @ViewById(R.id.hint_for_user_text)
    View hintText;

    @AfterViews
    void tryAutoclose() {
        shouldAutoclose = !pref.isFirstRun().get() && !explicitCall;
        if (shouldAutoclose) {
            autoclose();
        } else {
            hintText.setVisibility(View.VISIBLE);
        }
        pref.isFirstRun().put(false);
    }

    @Background
    void autoclose() {
        try {
            Log.v(TAG, "I should autoclose");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            Log.w(TAG, "", e);
        }
        close();
    }

    @Click(R.id.main_layout)
    void close() {
        ((FragmentManagingActivity) getActivity()).cancel();
    }
}
