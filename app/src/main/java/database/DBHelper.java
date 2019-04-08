package database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DBName="document.db";
    private static int DBVersion=1;
    private static DBHelper instance=null;

    //singelton method to access database from other activity
    public static synchronized DBHelper getsInstance(Context context){
        if(instance==null){
            instance=new DBHelper(context.getApplicationContext());
        }
        return instance;
    }


    public DBHelper(Context context) {
        super(context, DBName, null, DBVersion);
    }

    //create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS "+DBC.PersonsTable.PersonsTableName+
                    " ("+DBC.PersonsTable.Id+" INTEGER PRIMARY key AUTOINCREMENT,"+
                    DBC.PersonsTable.PersonName+" TEXT NOT NULL,"+
                    DBC.PersonsTable.DocumentNumber+" INTEGER,"+
                    DBC.PersonsTable.PersonImage+" BLOB)");
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    //Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS "+DBC.PersonsTable.PersonsTableName);
            onCreate(db);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    //Get all persons
    public Cursor getPersons(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        try {
            cursor=db.rawQuery("SELECT * FROM "+ DBC.PersonsTable.PersonsTableName,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cursor;
    }

    //Get certain person with id number
    public Cursor personsGetDataById(String id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        try{
            cursor=db.rawQuery("SELECT * FROM "+DBC.PersonsTable.PersonsTableName+" WHERE "+
                    DBC.PersonsTable.Id+"='"+id+"'",null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cursor;
    }

    //Get certain person with person name
    public Cursor personsGetDataByName(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        try{
            cursor=db.rawQuery("SELECT * FROM "+DBC.PersonsTable.PersonsTableName+" WHERE "+
                    DBC.PersonsTable.PersonName+"='"+name+"'",null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cursor;
    }

    //Insert data
    public long personsInsert(String personName,String documentNumber,byte[] personImage){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBC.PersonsTable.PersonName,personName);
        contentValues.put(DBC.PersonsTable.DocumentNumber,documentNumber);
        contentValues.put(DBC.PersonsTable.PersonImage,personImage);
        return db.insert(DBC.PersonsTable.PersonsTableName,null,contentValues);
    }

    //delete data
    public Integer deleteData(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(DBC.PersonsTable.PersonsTableName,DBC.PersonsTable.Id+"=?",new String[]{String.valueOf(id)});
    }
}
