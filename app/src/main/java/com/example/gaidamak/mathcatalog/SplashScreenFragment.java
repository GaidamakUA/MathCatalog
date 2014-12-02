package com.example.gaidamak.mathcatalog;

import android.app.Fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_splash_screen)
public class SplashScreenFragment extends Fragment {
    private static final String TAG = "SplashScreenFragment";

    /**
     * Closing fragment (call to main activity)
     */
    @Click(R.id.main_layout)
    void close() {
        ((FragmentManagingActivity) getActivity()).cancel();
    }
}
