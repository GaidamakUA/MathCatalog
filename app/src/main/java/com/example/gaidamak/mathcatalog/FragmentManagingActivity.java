package com.example.gaidamak.mathcatalog;

/**
 * Interface to fix the contract between fragments and activity
 */
public interface FragmentManagingActivity {
    public void viewMathTerm(long id);
    public void editMathTerm(long id);
    public void addNewTerm();
    public void cancel();
    public void showSplashScreen(boolean explicitCall);
}
