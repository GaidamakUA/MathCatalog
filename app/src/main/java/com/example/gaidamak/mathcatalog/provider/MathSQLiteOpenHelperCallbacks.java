package com.example.gaidamak.mathcatalog.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gaidamak.mathcatalog.BuildConfig;
import com.example.gaidamak.mathcatalog.JsonItem;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermColumns;
import com.example.gaidamak.mathcatalog.provider.mathterm.MathTermContentValues;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Implement your custom database creation or upgrade code here.
 * <p/>
 * This file will not be overwritten if you re-run the content provider generator.
 */
public class MathSQLiteOpenHelperCallbacks {
    private static final String TAG = MathSQLiteOpenHelperCallbacks.class.getSimpleName();

    public void onOpen(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onOpen");
        // Insert your db open code here.
    }

    public void onPreCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPreCreate");
        // Insert your db creation code here. This is called before your tables are created.
    }

    public void onPostCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPostCreate");
        // Insert your db creation code here. This is called after your tables are created.
        try {
            // Parsing XML and writing data to DB
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new BufferedReader(
                    new InputStreamReader(context.getAssets().open("db_init.json"))));
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                // TODO replace with constant
                if (name.equals("items")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        JsonItem item = gson.fromJson(reader, JsonItem.class);
                        MathTermContentValues contentValues = new MathTermContentValues();
                        contentValues.putMathTerm(item.title);
                        // TODO load from data
                        String url = item.url == null ? "" : item.url;
                        contentValues.putUrl(url);
                        contentValues.putMathFormula(item.formula);
                        contentValues.putDescription(item.description);
                        contentValues.putTags(item.tags);
                        db.insert(MathTermColumns.TABLE_NAME, null, contentValues.values());
                    }
                } else {
                    throw new IllegalArgumentException("unexpected root value:" + name);
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.w(TAG, "", e);
        }
    }

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Insert your upgrading code here.
    }
}
