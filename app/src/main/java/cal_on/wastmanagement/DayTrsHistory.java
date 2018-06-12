package cal_on.wastmanagement;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cal_on.wastmanagement.NewDatabase.DBHelperreports;
import cal_on.wastmanagement.NewDatabase.Database;

/**
 * Created by Cal_on on 9/11/2017.
 */

public class DayTrsHistory extends Activity {
    String date;
    SQLiteDatabase data;
    ListView l;
    int count;
    private static TextView textView;

    boolean blueToothconnected = false;
    private final String TAG = MainActivity.class.getSimpleName();
    byte[] imagedata = null;




    ImageView linefeed;



    public String font_Size_38(String paramString)
    {
        return String.valueOf(new String(new byte[] { 27, 75, 14 })) + paramString;
    }
    BTConnection uConn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daytrshistory);
        l=(ListView)findViewById(R.id.listView_cr);
       textView=(TextView) findViewById(R.id.text);
        Intent in=getIntent();
        date=in.getStringExtra("date_rep");
        DBHelperreports mydbhelper=new DBHelperreports(this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
        data=mydbhelper.getWritableDatabase();
        Cursor c_count= data.rawQuery("select distinct "+Database.BD_BILL_NO+" from "+Database.BD_TABLE_NAME+" where "+Database.BD_DATE+" = '"+date+"'",null);
        count=c_count.getCount();
        String bill_no[]=new String[count];
        String date[]=new String[count];
        final String name[]=new String[count];
        String item_no[]=new String[count];
        String Amount[]=new String[count];
        // String item_total[]=new String[count];
        String status[]=new String[count];
        String total_amt[]=new String[count];
        String amt_paid[]=new String[count];
        try{
            String col1[]=new String[]{Database.BI_BILL_NAME,Database.BI_BILL_DATE,Database.BI_BILL_NO,Database.BI_BILL_ITEM_COUNT,Database.BI_BILL_TOTAL,Database.BI_AMT_PAID,Database.BI_AMT_PENDING,Database.BI_PAYMENT_STATUS,Database.BI_DESC};
            Cursor c1=data.query(Database.BI_TABLE_NAME, col1, null, null, null, null, null);
            int i=0;
            for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()) {

                    name[i] = c1.getString(0);
                    date[i] = c1.getString(1);
                    bill_no[i] = c1.getString(2);
                    item_no[i] = c1.getString(3);
                    Amount[i] = c1.getString(8);
                    amt_paid[i] = c1.getString(5);
                    total_amt[i] = c1.getString(6);
                    status[i] = c1.getString(7);
                    i++;

                }

        }catch(Exception e){

        }
        l.addHeaderView(new View(this));
        l.addFooterView(new View(this));
        l.setAdapter(new DayHistTransactionAdapter(this,bill_no,name,date,Amount,amt_paid,total_amt,status));
        registerForContextMenu(l);

        linefeed = (ImageView) findViewById(R.id.Print);
        linefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = Environment.getExternalStorageState();
                if (!(state.equals(Environment.MEDIA_MOUNTED))) {
                    Toast.makeText(DayTrsHistory.this, "There is no any sd card", Toast.LENGTH_LONG).show();


                } else {
                    BufferedReader reader = null;
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy_MM_dd");
                    Date now1 = new Date();
                    String fileName11 = formatter1.format(now1) + ".xls";
                    try {
                        // Toast.makeText(DayTrsHistory.this, "Sd card available", Toast.LENGTH_LONG).show();
                        File file = Environment.getExternalStorageDirectory();
                        File textFile = new File(file.getAbsolutePath()+File.separator + "");
                        File gpxfile = new File(textFile, fileName11);
                        reader = new BufferedReader(new FileReader(gpxfile));
                        StringBuilder textBuilder = new StringBuilder();
                        String line;
                        while((line = reader.readLine()) != null) {
                            textBuilder.append(line);
                            textBuilder.append("\n");

                        }
                        textView.setText(textBuilder);

                    } catch (FileNotFoundException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    finally{
                        if(reader != null){
                            try {
                                reader.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                }
                try {
                    String str7 =textView.getText().toString();
                    String str8 = font_Size_38(str7);
                    str8 += "\n";
                    uConn.printData(str8);
                    Toast.makeText(DayTrsHistory.this, "Day Report Is Printing", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {

                    e.printStackTrace();

                    Toast.makeText(DayTrsHistory.this, "Unable to Print the data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        uConn = new BTConnection();

    }
}
