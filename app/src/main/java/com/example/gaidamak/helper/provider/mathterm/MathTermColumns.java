package com.example.gaidamak.helper.provider.mathterm;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.gaidamak.helper.provider.MathProvider;

/**
 * Entity which represents math term
 */
public class MathTermColumns implements BaseColumns {
    public static final String TABLE_NAME = "math_term";
    public static final Uri CONTENT_URI = Uri.parse(MathProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    /**
     * Name of math term
     */
    public static final String MATH_TERM = "math_term";

    /**
     * Url
     */
    public static final String URL = "url";

    /**
     * Name of math term lowercase, (workaround, case insensitive LIKE for SQLite does not working for non ASCII characters)
     */
    public static final String MATH_TERM_LOWERCASE = "math_term_lowercase";

    /**
     * Math formula if present
     */
    public static final String MATH_FORMULA = "math_formula";

    /**
     * Description of math term
     */
    public static final String DESCRIPTION = "description";

    /**
     * Tags of math term
     */
    public static final String TAGS = "tags";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." + _ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[]{
            _ID,
            MATH_TERM,
            URL,
            MATH_TERM_LOWERCASE,
            MATH_FORMULA,
            DESCRIPTION,
            TAGS
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == MATH_TERM || c.contains("." + MATH_TERM)) return true;
            if (c == URL || c.contains("." + URL)) return true;
            if (c == MATH_TERM_LOWERCASE || c.contains("." + MATH_TERM_LOWERCASE)) return true;
            if (c == MATH_FORMULA || c.contains("." + MATH_FORMULA)) return true;
            if (c == DESCRIPTION || c.contains("." + DESCRIPTION)) return true;
            if (c == TAGS || c.contains("." + TAGS)) return true;
        }
        return false;
    }

}
