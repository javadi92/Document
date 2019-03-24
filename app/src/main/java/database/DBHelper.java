package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DBName="document.db";
    private static int DBVersion=1;

    private static DBHelper instance;

    public static synchronized DBHelper getsInstance(Context context){
        if(instance==null){
            instance=new DBHelper(context.getApplicationContext());
        }
        return instance;
    }


    private DBHelper(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS "+DBC.PersonsTable.PersonsTableName+
                    " ("+DBC.PersonsTable.Id+" INTEGER PRIMARY key AUTOINCREMENT,"+
                    DBC.PersonsTable.PersonName+" TEXT NOT NULL,"+
                    DBC.PersonsTable.DocumentNumber+" INTEGER,"+
                    DBC.PersonsTable.PersonImage+" INTEGER)");
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

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
}
