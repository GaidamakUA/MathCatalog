package com.example.gaidamak.mathcatalog.provider.mathterm;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import com.example.gaidamak.mathcatalog.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code math_term} table.
 */
public class MathTermContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MathTermColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, MathTermSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Name of math term
     */
    public MathTermContentValues putMathTerm(String value) {
        if (value == null) throw new IllegalArgumentException("value for mathTerm must not be null");
        // This is used to provide case insensitive search
        // and do not force user to call same method twice
        mContentValues.put(MathTermColumns.MATH_TERM, value);
        this.putMathTermLowercase(value.toLowerCase());
        return this;
    }



    /**
     * Name of math term lowercase, (workaround, case insensitive LIKE for SQLite does not working for non ASCII characters)
     * Called from putMathTerm
     */
    public MathTermContentValues putMathTermLowercase(String value) {
        if (value == null) throw new IllegalArgumentException("value for mathTermLowercase must not be null");
        mContentValues.put(MathTermColumns.MATH_TERM_LOWERCASE, value);
        return this;
    }



    /**
     * Math formula if present
     */
    public MathTermContentValues putMathFormula(String value) {
        mContentValues.put(MathTermColumns.MATH_FORMULA, value);
        return this;
    }

    public MathTermContentValues putMathFormulaNull() {
        mContentValues.putNull(MathTermColumns.MATH_FORMULA);
        return this;
    }


    /**
     * Description of math term
     */
    public MathTermContentValues putDescription(String value) {
        if (value == null) throw new IllegalArgumentException("value for description must not be null");
        mContentValues.put(MathTermColumns.DESCRIPTION, value);
        return this;
    }



    /**
     * Tags of math term
     */
    public MathTermContentValues putTags(String value) {
        mContentValues.put(MathTermColumns.TAGS, value);
        return this;
    }

    public MathTermContentValues putTagsNull() {
        mContentValues.putNull(MathTermColumns.TAGS);
        return this;
    }

}
