package com.example.booksale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

public class Dao  {
    private final MyHelper myHelper;
    private SQLiteDatabase db;
    private String TAG = "Dao";

    public Dao(Context context){
        myHelper = new MyHelper(context);
        db = myHelper.getWritableDatabase();
        /*调用dao ， 实例化一个MyHelper 传入Context*/
    }

    public void insert(String TABLE_NAME, ContentValues values){


        /*
        * */
        db = myHelper.getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public void delete(String TABLE_NAME,String whereClause,String[] column_values){

        db =myHelper.getWritableDatabase();
        db.delete(TABLE_NAME,whereClause,column_values);
        db.close();

    }



    /*public Cursor queryALL(String TABLE_NAME,String selection,String[] selection_values,String groupby ){
        String selections = selection+"=?";

        db = myHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, selections, selection_values, groupby, null,null,null);
        int tbc_length = cursor.getColumnCount();
        while(cursor.moveToNext()){

            for(int i = 0; i<tbc_length; i++){
                String data = cursor.getString(i);
                Log.i(TAG,"data:"+data);
            }
        }
        cursor.close();
        db.close();
    }*/
    public void update(String TABLE_NAME,ContentValues updatevalues,String column_name,String[] column_values){
        db = myHelper.getWritableDatabase();
        String whereClause = column_name + "=?" ;
        db.update(TABLE_NAME,updatevalues,whereClause,column_values);
        db.close();
    }
}
