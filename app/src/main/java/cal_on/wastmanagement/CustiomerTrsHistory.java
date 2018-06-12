package cal_on.wastmanagement;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cal_on.wastmanagement.NewDatabase.DBHelperreports;
import cal_on.wastmanagement.NewDatabase.Database;

/**
 * Created by Cal_on on 9/21/2017.
 */

public class CustiomerTrsHistory extends Activity {
    String Name;
    SQLiteDatabase data;
    ListView l;
    int count;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customertrshistory);
        l=(ListView)findViewById(R.id.listView_cr);
        Intent in=getIntent();
        Name=in.getStringExtra("Bill_Name");
        DBHelperreports mydbhelper=new DBHelperreports(this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
        data=mydbhelper.getWritableDatabase();
        Cursor c_count= data.rawQuery("select distinct "+Database.BD_BILL_NO+" from "+Database.BD_TABLE_NAME+" where "+Database.BD_NAME+" = '"+Name+"'",null);
        count=c_count.getCount();
        String bill_no[]=new String[count];
        String date[]=new String[count];
        String name[]=new String[count];
        String item_no[]=new String[count];
        String Amount[]=new String[count];
        // String item_total[]=new String[count];
        String status[]=new String[count];
        String total_amt[]=new String[count];
        String amt_paid[]=new String[count];
        try{
            String col1[]=new String[]{Database.BI_BILL_NAME,
                    Database.BI_BILL_DATE,Database.BI_BILL_NO,Database.BI_BILL_ITEM_COUNT,Database.BI_BILL_TOTAL,Database.BI_AMT_PAID,Database.BI_AMT_PENDING,Database.BI_PAYMENT_STATUS,Database.BI_DESC};
            Cursor c1=data.query(Database.BI_TABLE_NAME, col1, null, null, null, null, null);
            int i=0;
            for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){

                name[i]=c1.getString(0);
                date[i]=c1.getString(1);
                bill_no[i]=c1.getString(2);
                item_no[i]=c1.getString(3);
                Amount[i]=c1.getString(8);
                amt_paid[i]=c1.getString(5);
                total_amt[i]=c1.getString(6);
                status[i]=c1.getString(7);
                i++;

            }
        }catch(Exception e){

        }
        l.addHeaderView(new View(this));
        l.addFooterView(new View(this));
      //  l.setAdapter(new TransactionAdapter(this,name,date,bill_no,Amount,amt_paid,total_amt));
        registerForContextMenu(l);
        AdapterView.OnItemClickListener o=new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
                // TODO Auto-generated method stub
                DBHelperreports mydbhelper=new DBHelperreports(CustiomerTrsHistory.this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
                data=mydbhelper.getWritableDatabase();
                String cols[]=new String[]{Database.BI_BILL_NO,Database.BI_BILL_TOTAL};
                Cursor c=data.query(Database.BI_TABLE_NAME, cols, null, null, null, null, null);
                int co=c.getCount();
                c.moveToPosition(co-pos);
                Intent i=new Intent(CustiomerTrsHistory.this,BillSummary.class);
                i.putExtra("bill_no", c.getString(0));
                // i.putExtra("bill_total", c.getString(1));
                startActivity(i);

            }

        };
        l.setOnItemClickListener(o);

    }
}
