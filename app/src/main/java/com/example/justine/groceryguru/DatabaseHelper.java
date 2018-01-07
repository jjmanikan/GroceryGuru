package com.example.justine.groceryguru;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LIST_TABLE = "list_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "list_group";
    private static final String COL2 = "name";

    public DatabaseHelper(Context context){
        super(context, LIST_TABLE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + LIST_TABLE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT, " + COL2 + " TEXT )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        onCreate(db);
    }

    /*
    *  Methods to control lists
    */

    public void addTitle(String item){
        int group = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, group);
        contentValues.put(COL2, item);
        db.insert(LIST_TABLE, null, contentValues);
    }

    public Cursor getTitleData(){
        int group = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + LIST_TABLE + " WHERE " + COL1 + " = '" + group + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getTitleItemId(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + LIST_TABLE + " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteTitleName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + LIST_TABLE + " WHERE " + COL0 + " = '" + id + "'";
        db.execSQL(query);
        deleteIndvListItemName(id);
    }

    /*
    *   Methods to control information in individual lists
    */

    public void addListItem(String item, int group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, group);
        contentValues.put(COL2, item);

        db.insert(LIST_TABLE, null, contentValues);
    }

    public Cursor getListItemData(int group){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + LIST_TABLE + " WHERE " + COL1 + " = '" + group + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getListItemId(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + LIST_TABLE + " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteListItemName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + LIST_TABLE + " WHERE " + COL0 + " = '" + id + "'";
        db.execSQL(query);
    }

    public void deleteIndvListItemName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + LIST_TABLE + " WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);
    }

}
