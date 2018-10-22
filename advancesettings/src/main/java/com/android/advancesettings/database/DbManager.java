package com.android.advancesettings.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {
    private static int VERSION = 1;
    private static final String DB_NAME = "shell.db";
    private static final String CREATE_TABLE_ITEM = "CREATE TABLE IF NOT EXISTS Shell(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT, instruction TEXT, type INTEGER, path TEXT, checkOn TEXT, checkOff TEXT)";
    private static final String CREATE_TEMP_NEW = "ALTER TABLE Shell RENAME TO _shell";
    private static final String INSERT_DATA = "INSERT INTO Shell SELECT *,'' FROM _shell";
    private static final String DROP_TEMP_NEW = "DROP TABLE _shell";
    public DbManager(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            db.execSQL(CREATE_TEMP_NEW);
            db.execSQL(CREATE_TABLE_ITEM);
            db.execSQL(INSERT_DATA);
            db.execSQL(DROP_TEMP_NEW);
        }
    }
}
