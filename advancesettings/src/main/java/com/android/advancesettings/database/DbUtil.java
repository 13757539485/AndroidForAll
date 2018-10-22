package com.android.advancesettings.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.advancesettings.constant.Constant;
import com.android.advancesettings.view.ShellView;

import java.util.ArrayList;

public class DbUtil {

    private final DbManager mDbManager;

    public DbUtil(Context context) {
        mDbManager = new DbManager(context);
    }

    public void insert(ShellView shellView) {
        SQLiteDatabase sqLiteDatabase = mDbManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.NAME, shellView.getName());
        values.put(Constant.INSTRUCTION, shellView.getInstruction());
        values.put(Constant.TYPE, shellView.getType());
        values.put(Constant.PATH, shellView.getPath());
        values.put(Constant.CHECKON, shellView.getCheckOn());
        values.put(Constant.CHECKOFF, shellView.getCheckOff());
        sqLiteDatabase.insert(Constant.TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public void delete(int id) {
        SQLiteDatabase sqLiteDatabase = mDbManager.getWritableDatabase();
        sqLiteDatabase.delete(Constant.TABLE_NAME, Constant._ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void update(ShellView shellView) {
        SQLiteDatabase sqLiteDatabase = mDbManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.NAME, shellView.getName());
        values.put(Constant.INSTRUCTION, shellView.getInstruction());
        values.put(Constant.TYPE, shellView.getType());
        values.put(Constant.PATH, shellView.getPath());
        values.put(Constant.CHECKON, shellView.getCheckOn());
        values.put(Constant.CHECKOFF, shellView.getCheckOff());
        sqLiteDatabase.update(Constant.TABLE_NAME, values, Constant._ID + "=?", new String[]{String.valueOf(shellView.getId())});
        sqLiteDatabase.close();
    }

    public ArrayList<ShellView> findAll() {
        SQLiteDatabase sqLiteDatabase = mDbManager.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(Constant.TABLE_NAME, null, null, null, null, null, null, null);
        ArrayList<ShellView> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String instruction = cursor.getString(2);
            int type = cursor.getInt(3);
            String path= cursor.getString(4);
            String checkOn= cursor.getString(5);
            String checkOff= cursor.getString(6);
            items.add(new ShellView(id, name, instruction, type, path, checkOn, checkOff));
        }
        cursor.close();
        sqLiteDatabase.close();
        return items;
    }
}
