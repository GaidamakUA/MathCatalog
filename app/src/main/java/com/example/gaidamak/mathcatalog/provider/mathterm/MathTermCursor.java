package com.example.gaidamak.mathcatalog.provider.mathterm;

import java.util.Date;

import android.database.Cursor;

import com.example.gaidamak.mathcatalog.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code math_term} table.
 */
public class MathTermCursor extends AbstractCursor {
    public MathTermCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Name of math term
     * Cannot be {@code null}.
     */
    public String getMathTerm() {
        Integer index = getCachedColumnIndexOrThrow(MathTermColumns.MATH_TERM);
        return getString(index);
    }

    /**
     * Name of math term lowercase, (workaround, case insensitive LIKE for SQLite does not working for non ASCII characters)
     * Cannot be {@code null}.
     */
    public String getMathTermLowercase() {
        Integer index = getCachedColumnIndexOrThrow(MathTermColumns.MATH_TERM_LOWERCASE);
        return getString(index);
    }

    /**
     * Math formula if present
     * Can be {@code null}.
     */
    public String getMathFormula() {
        Integer index = getCachedColumnIndexOrThrow(MathTermColumns.MATH_FORMULA);
        return getString(index);
    }

    /**
     * Description of math term
     * Cannot be {@code null}.
     */
    public String getDescription() {
        Integer index = getCachedColumnIndexOrThrow(MathTermColumns.DESCRIPTION);
        return getString(index);
    }

    /**
     * Tags of math term
     * Can be {@code null}.
     */
    public String getTags() {
        Integer index = getCachedColumnIndexOrThrow(MathTermColumns.TAGS);
        return getString(index);
    }
}
