package com.example.gaidamak.mathcatalog;

/**
 * Created by gaidamak on 29.11.14.
 */
public interface FragmentManagingActivity {
    public void viewMathTerm(long id);
    public void editMathTerm(long id);
    public void addNewTerm();
    public void cancel();
}
