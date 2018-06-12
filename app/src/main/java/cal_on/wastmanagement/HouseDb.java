package cal_on.wastmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Cal_on on 8/8/2017.
 */

public class HouseDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBName.db";
    public static final String CAT_TABLE_NAME = "itemstable";
    public static final String CAT_COLUMN_ID = "id";
    public static final String CAT_COLUMN_ItemCost = "itemcost";
    public static final String CAT_COLUMN_Itemkg = "itemkg";
    public static final String CAT_COLUMN_ItemQuantity = "itemQuantity";

    private static final int DATABASE_VERSION = 1;



    private HashMap hp;

    public HouseDb(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table itemstable " +
                        "(id integer primary key, itemcost text,itemkg text, itemQuantity text)"
        );

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS itemstable");
        onCreate(db);
    }

    public String insertContact(String itemcost,String itemkg,String itemQuantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("itemcost", itemcost);
        contentValues.put("itemkg", itemkg);
        contentValues.put("itemQuantity", itemQuantity);

        db.insert("itemstable", null, contentValues);
        return itemcost;



    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from itemstable where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CAT_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String itemname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemcost", itemname);
        db.update("itemstable", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("itemstable",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from itemstable  ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            array_list.add(res.getString(res.getColumnIndex(CAT_COLUMN_ItemCost)));

            res.moveToNext();
        }
        return array_list;
    }
    public List<Integer> getAllCotacts1(){
        List<Integer> labels = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from itemstable  ", null);

        res.moveToFirst();

        while(res.isAfterLast()== false){

            labels.add(res.getInt(res.getColumnIndex(CAT_COLUMN_ItemQuantity)));

            res.moveToNext();
        }
        return labels;
    }
    public ArrayList<String> getAllCotacts2()
    {
        ArrayList<String> array_list2 = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from itemstable  ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            array_list2.add(res.getString(res.getColumnIndex(CAT_COLUMN_Itemkg)));

            res.moveToNext();
        }
        return array_list2;
    }
}
