package com.example.gaidamak.helper.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.example.gaidamak.helper.BuildConfig;
import com.example.gaidamak.helper.provider.mathterm.MathTermColumns;

public class MathSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = MathSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "math.db";
    private static final int DATABASE_VERSION = 1;
    private static MathSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final MathSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    private static final String SQL_CREATE_TABLE_MATH_TERM = "CREATE TABLE IF NOT EXISTS "
            + MathTermColumns.TABLE_NAME + " ( "
            + MathTermColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MathTermColumns.MATH_TERM + " TEXT NOT NULL, "
            + MathTermColumns.URL + " TEXT NOT NULL, "
            + MathTermColumns.MATH_TERM_LOWERCASE + " TEXT NOT NULL, "
            + MathTermColumns.MATH_FORMULA + " TEXT, "
            + MathTermColumns.DESCRIPTION + " TEXT NOT NULL, "
            + MathTermColumns.TAGS + " TEXT "
            + " );";

    // @formatter:on

    public static MathSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static MathSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static MathSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new MathSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private MathSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mOpenHelperCallbacks = new MathSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static MathSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new MathSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MathSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new MathSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_MATH_TERM);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
