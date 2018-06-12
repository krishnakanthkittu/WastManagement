package cal_on.wastmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cal_on.wastmanagement.NewDatabase.DBHelperreports;
import cal_on.wastmanagement.NewDatabase.Database;

/**
 * Created by Cal_on on 8/9/2017.
 */

public class Reports  extends Activity  {
    ListView l;
    SQLiteDatabase mydatabase;
    int count;
    double partial=0,d=0;
    EditText namereport;
    double amt_pending1,amt_paid1,total1;
    String bill_no,name;
    ImageView cancle;
    ReportAdapter adapter;
    ArrayList<WorldReport> arraylist = new ArrayList<WorldReport>();
    double dp=0;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);
        l=(ListView)findViewById(R.id.listView_tr);
        cancle=(ImageView)findViewById(R.id.cancle1);
        namereport=(EditText)findViewById(R.id.namereport);
        DBHelperreports mydbhelper=new DBHelperreports(this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
        mydatabase=mydbhelper.getWritableDatabase();
        Cursor c_count= mydatabase.rawQuery("select distinct "+Database.BI_BILL_NO+" from "+Database.BI_TABLE_NAME,null);
        count=c_count.getCount();
        amt_pending1=0;
        amt_paid1=0;
        total1=0;
        String bill_no[]=new String[count];
        String date[]=new String[count];
        String name[]=new String[count];
        String item_no[]=new String[count];
        String Amount[]=new String[count];
        String status[]=new String[count];
        String total_amt[]=new String[count];
        String amt_paid[]=new String[count];
        try{
            String col1[]=new String[]{Database.BI_BILL_NAME,Database.BI_BILL_DATE,Database.BI_BILL_NO,Database.BI_BILL_ITEM_COUNT,Database.BI_BILL_TOTAL,Database.BI_AMT_PAID,Database.BI_AMT_PENDING,Database.BI_PAYMENT_STATUS,Database.BI_DESC};
            Cursor c1=mydatabase.query(Database.BI_TABLE_NAME, col1, null, null, null, null, null);
            int i=0;

            for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
                name[i]=c1.getString(0);
                date[i]=c1.getString(1);
                bill_no[i]=c1.getString(2);
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
        l.setAdapter(new TransactionAdapter(this,name,date,bill_no,Amount,amt_paid,total_amt,status));
        AdapterView.OnItemClickListener o=new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
                // TODO Auto-generated method stub
                DBHelperreports mydbhelper=new DBHelperreports(Reports.this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
                mydatabase=mydbhelper.getWritableDatabase();
                String cols[]=new String[]{Database.BI_BILL_NO,Database.BI_BILL_TOTAL};
                Cursor c=mydatabase.query(Database.BI_TABLE_NAME, cols, null, null, null, null, null);
                int co=c.getCount();
                c.moveToPosition(co-pos);
                Intent i=new Intent(Reports.this,BillSummary.class);
                i.putExtra("bill_no", c.getString(0));

                startActivity(i);

            }

        };
        l.setOnItemClickListener(o);
        for (int i = 0; i < bill_no.length; i++)
        {
            WorldReport wp = new WorldReport(bill_no[i],name[i],date[i],Amount[i],amt_paid[i],total_amt[i],status[i]);
String str=""+wp;
            arraylist.add(wp);
        }


        adapter = new ReportAdapter(this, arraylist);

        l.setAdapter(adapter);

        namereport.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = namereport.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             namereport.setText("");


            }
        });
    }


}
