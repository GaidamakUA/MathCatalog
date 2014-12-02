package com.example.gaidamak.mathcatalog;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface SharePreferences {
    @DefaultBoolean(true)
    boolean isFirstRun();
}
