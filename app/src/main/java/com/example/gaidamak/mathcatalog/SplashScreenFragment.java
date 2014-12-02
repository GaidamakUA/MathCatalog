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

    /**
     * Value injected with EFragment.builder
     */
    @FragmentArg
    boolean explicitCall = false;
    boolean shouldAutoclose = true;
    @ViewById(R.id.hint_for_user_text)
    View hintText;

    /**
     * You should be familiar with this already. But, just in case
     * {@link org.androidannotations.annotations.AfterViews}
     */
    @AfterViews
    void tryAutoclose() {
        // Should autoclose if not first run.
        shouldAutoclose = !pref.isFirstRun().get() && !explicitCall;
        if (shouldAutoclose) {
            autoclose();
        } else {
            hintText.setVisibility(View.VISIBLE);
        }
        pref.isFirstRun().put(false);
    }

    /**
     * Creates new background thread, delays for 3 seconds and closing fragment;
     */
    @Background
    void autoclose() {
        try {
            Log.v(TAG, "I should autoclose");
            // Delay
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            Log.w(TAG, "", e);
        }
        close();
    }

    /**
     * Closing fragment (call to main activity)
     */
    @Click(R.id.main_layout)
    void close() {
        ((FragmentManagingActivity) getActivity()).cancel();
    }
}
