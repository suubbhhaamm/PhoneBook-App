package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{

    private static final String DB_NAME="Phone_Book";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="record";
    private static final String ID_COL="id";
    private static final String NAME_COL="name";
    private static final String CONTACT_COL="contact";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+" ("+ID_COL+
                " INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME_COL+" TEXT,"+ CONTACT_COL + " TEXT)";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create table again
        onCreate(db);
    }
    public boolean insertRecord(String name, String contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME_COL,name);
        values.put(CONTACT_COL,contact);
        long result = db.insert(TABLE_NAME,null,values);
        db.close();
        if(result == -1) {
            return false;
        }else{
            return true;
        }

    }
    public Cursor getRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from record", null);

        return cursor;
    }
    public boolean updateRecord(String id,String name, String contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME_COL,name);
        values.put(CONTACT_COL,contact);
        Cursor cursor = db.rawQuery("Select * from record where id = ?", new String[]{id});

        if(cursor.getCount() > 0){
            long result = db.update(TABLE_NAME,values,"id=?",new String[]{id});
            db.close();
            if(result == -1)
                return false;
            else
                return true;
        }else{
            db.close();
            return false;
        }
    }
    public boolean deleteRecord(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from record where id = ?", new String[]{id});

        if(cursor.getCount() > 0){
            long result = db.delete(TABLE_NAME,"id=?",new String[]{id});
            db.close();
            if(result == -1)
                return false;
            else
                return true;
        }else {
            db.close();
            return false;
        }
    }
}