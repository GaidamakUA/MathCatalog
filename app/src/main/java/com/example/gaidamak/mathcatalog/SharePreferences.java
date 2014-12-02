package com.example.gaidamak.mathcatalog;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Interface to work with {@link android.content.SharedPreferences}
 *
 * @see org.androidannotations.annotations.sharedpreferences.SharedPref
 */
@SharedPref
public interface SharePreferences {
    /**
     * indicated whether this run is first one
     * @return
     */
    @DefaultBoolean(true)
    boolean isFirstRun();
}
