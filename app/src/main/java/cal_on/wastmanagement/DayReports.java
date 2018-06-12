package cal_on.wastmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import cal_on.wastmanagement.NewDatabase.DBHelperreports;
import cal_on.wastmanagement.NewDatabase.Database;

/**
 * Created by Cal_on on 8/9/2017.
 */

public class DayReports  extends Activity  {
    ListView l;
    String date[];
    double total[];
    SQLiteDatabase data;
    int count;
    TextView namereport;
    LinearLayout visble1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayreports);

      //  visble1=(LinearLayout)findViewById(R.id.visble1);
      //  searchView=(SearchView)findViewById(R.id.nameserch);

        l=(ListView)findViewById(R.id.listView_tr);
        update();

      AdapterView.OnItemClickListener o1=new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
                // TODO Auto-generated method stub
                DBHelperreports mydbhelper=new DBHelperreports(DayReports.this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
                data=mydbhelper.getWritableDatabase();
                String cols[]=new String[]{Database.BD_DATE};
                Cursor c=data.query(Database.BD_TABLE_NAME, cols, null, null, null, null, null);
                int co=c.getCount();
                c.moveToPosition(pos);
                Intent i=new Intent(DayReports.this,DayTrsHistory.class);
                i.putExtra("date_rep", c.getString(0));
                startActivity(i);

            }

        };

        l.setOnItemClickListener(o1);
    }

    private void update() {
        // TODO Auto-generated method stub
        DBHelperreports mydbhelper=new DBHelperreports(this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
        data=mydbhelper.getWritableDatabase();
        Cursor c_count= data.rawQuery("select * from "+Database.BD_TABLE_NAME,null);
        count=c_count.getCount();
        date=new String[count];
        int i=0;
        String col1[]=new String[]{Database.BD_PARTICULARS,Database.BD_LABOUR_CHARGES,Database.BD_ITEM_ID,Database.BD_ITEM_TOTAL,Database.BD_BILL_NO,Database.BD_GOLD_WT,Database.BD_NAME,Database.BD_SILVER_WT,Database.BD_DATE,Database.BD_BILL_NO };
        Cursor c1=data.query(Database.BD_TABLE_NAME, col1, null, null, null, null, null);
      for(c1.moveToFirst();!c1.isAfterLast();c1.moveToNext()){
            date[i]=c1.getString(8);
               //i++;
      }

        String col[]=new String[]{Database.BI_BILL_DATE,Database.BI_DESC};
        Cursor c=data.query(Database.BI_TABLE_NAME, col, null, null, null, null, null);
        int j=0;
        total=new double[count];
        int k=i;
        i=0;
      //  while(i!=k){
            total[i]=0;
            for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious()){
                if(date[i].equals(c.getString(0))){
                    total[i]+=Double.parseDouble(c.getString(1));
                }

            }

        i++;


      // }
        l.setAdapter(new DayTransactionAdapter(DayReports.this,date,total));
        registerForContextMenu(l);


    }



}
