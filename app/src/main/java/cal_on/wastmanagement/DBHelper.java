package cal_on.wastmanagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CAT_TABLE_NAME = "category";
    public static final String CAT_COLUMN_ID = "id";
    public static final String CAT_COLUMN_NAME = "cusname";
    public static final String CAT_COLUMN_Village = "cusvillage";
    public static final String CAT_COLUMN_mobileNum = "cusmoblienum";
    /*  public static final String CAT_COLUMN_ItemCost = "itemcost";
      public static final String CAT_COLUMN_Itemkg = "itemkg";
      public static final String CAT_COLUMN_ItemQuantity = "itemQuantity";
      public static final String CAT_COLUMN_ItemNAME = "itemname";*/
    private static final int DATABASE_VERSION = 1;



    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table category " +
                        "(id integer primary key, cusname text,cusvillage text,cusmoblienum text)"
        );

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS item");
        onCreate(db);
    }

    public String insertContact(String cusname,String cusvillage,String cusmoblienum)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cusname", cusname);
        contentValues.put("cusvillage", cusvillage);
        contentValues.put("cusmoblienum", cusmoblienum);
        db.insert("category", null, contentValues);
        return cusname;



    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from category where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CAT_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String cusname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cusname", cusname);
        db.update("category", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("category",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public  ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from category  ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CAT_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public  ArrayList<String> getAllCotacts1()
    {
        ArrayList<String> array_list1 = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from category  ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list1.add(res.getString(res.getColumnIndex(CAT_COLUMN_Village)));
            res.moveToNext();
        }
        return array_list1;
    }
    public  ArrayList<String> getAllCotacts2()
    {
        ArrayList<String> array_list2 = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from category  ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list2.add(res.getString(res.getColumnIndex(CAT_COLUMN_mobileNum)));
            res.moveToNext();
        }
        return array_list2;
    }
}