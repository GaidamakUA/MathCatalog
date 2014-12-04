package com.example.gaidamak.mathcatalog.provider.mathterm;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.gaidamak.mathcatalog.provider.base.AbstractSelection;

/**
 * Selection for the {@code math_term} table.
 */
public class MathTermSelection extends AbstractSelection<MathTermSelection> {
    @Override
    public Uri uri() {
        return MathTermColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection      A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder       How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *                        order, which may be unordered.
     * @return A {@code MathTermCursor} object, which is positioned before the first entry, or null.
     */
    public MathTermCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new MathTermCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public MathTermCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public MathTermCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public MathTermSelection id(long... value) {
        addEquals("math_term." + MathTermColumns._ID, toObjectArray(value));
        return this;
    }


    public MathTermSelection mathTerm(String... value) {
        addEquals(MathTermColumns.MATH_TERM, value);
        return this;
    }

    public MathTermSelection mathTermNot(String... value) {
        addNotEquals(MathTermColumns.MATH_TERM, value);
        return this;
    }

    public MathTermSelection mathTermLike(String... value) {
        addLike(MathTermColumns.MATH_TERM, value);
        return this;
    }

    public MathTermSelection url(String... value) {
        addEquals(MathTermColumns.URL, value);
        return this;
    }

    public MathTermSelection urlNot(String... value) {
        addNotEquals(MathTermColumns.URL, value);
        return this;
    }

    public MathTermSelection urlLike(String... value) {
        addLike(MathTermColumns.URL, value);
        return this;
    }

    public MathTermSelection mathTermLowercase(String... value) {
        addEquals(MathTermColumns.MATH_TERM_LOWERCASE, value);
        return this;
    }

    public MathTermSelection mathTermLowercaseNot(String... value) {
        addNotEquals(MathTermColumns.MATH_TERM_LOWERCASE, value);
        return this;
    }

    public MathTermSelection mathTermLowercaseLike(String... value) {
        addLike(MathTermColumns.MATH_TERM_LOWERCASE, value);
        return this;
    }

    public MathTermSelection mathFormula(String... value) {
        addEquals(MathTermColumns.MATH_FORMULA, value);
        return this;
    }

    public MathTermSelection mathFormulaNot(String... value) {
        addNotEquals(MathTermColumns.MATH_FORMULA, value);
        return this;
    }

    public MathTermSelection mathFormulaLike(String... value) {
        addLike(MathTermColumns.MATH_FORMULA, value);
        return this;
    }

    public MathTermSelection description(String... value) {
        addEquals(MathTermColumns.DESCRIPTION, value);
        return this;
    }

    public MathTermSelection descriptionNot(String... value) {
        addNotEquals(MathTermColumns.DESCRIPTION, value);
        return this;
    }

    public MathTermSelection descriptionLike(String... value) {
        addLike(MathTermColumns.DESCRIPTION, value);
        return this;
    }

    public MathTermSelection tags(String... value) {
        addEquals(MathTermColumns.TAGS, value);
        return this;
    }

    public MathTermSelection tagsNot(String... value) {
        addNotEquals(MathTermColumns.TAGS, value);
        return this;
    }

    public MathTermSelection tagsLike(String... value) {
        addLike(MathTermColumns.TAGS, value);
        return this;
    }
}
