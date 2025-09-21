package com.example.ottracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper implementation for persisting workers and overtime
 * counts. The current implementation defines the table and a simple
 * schema, but does not yet include methods for reading or writing
 * data. This provides a foundation for future enhancements such as
 * persistent storage across app launches.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ottracker.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_WORKERS = "workers";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_OPTOUT = "optout";
    public static final String COL_OTCOUNT = "otcount";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createWorkers = "CREATE TABLE " + TABLE_WORKERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_OPTOUT + " INTEGER, " +
                COL_OTCOUNT + " INTEGER)";
        db.execSQL(createWorkers);

        // Optional: insert initial demo crew
        db.execSQL("INSERT INTO workers (name, optout, otcount) VALUES ('Mike', 0, 0)");
        db.execSQL("INSERT INTO workers (name, optout, otcount) VALUES ('John', 0, 0)");
        db.execSQL("INSERT INTO workers (name, optout, otcount) VALUES ('Dave', 0, 0)");
        db.execSQL("INSERT INTO workers (name, optout, otcount) VALUES ('Chris', 0, 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKERS);
        onCreate(db);
    }
}
