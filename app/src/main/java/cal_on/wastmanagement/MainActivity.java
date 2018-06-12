package cal_on.wastmanagement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.gsm.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SimpleTimeZone;

import cal_on.wastmanagement.NewDatabase.DBHelperreports;
import cal_on.wastmanagement.NewDatabase.Database;

public class MainActivity extends AppCompatActivity {
    Spinner bluetooth_devices;
    private P25Connector mConnector;
    private ProgressDialog mProgressDlg;
    private ProgressDialog mConnectingDlg;
    private BluetoothAdapter mBluetoothAdapter;
    public static String BTAddress;
    boolean blueToothconnected = false;
    private final String TAG = MainActivity.class.getSimpleName();
    byte[] imagedata = null;
    protected static final int CAMERA_IMAGE_CAPTUE_OK = -1;
    protected static final int CAMERA_PIC_REQUEST = 1337;
    BTConnection uConn;
    static String imagesDir = Environment.getExternalStorageDirectory().getPath();
    String capturedImgSave;
    LinearLayout blueDisable;
    LinearLayout blueEnable;
    RadioGroup radiogroup;
    String payment_status,partial;
    String empty;
    private static EditText mDumpTextView,heighted;
    private static ScrollView mScrollView;
    private static   String totalData = "";
    private PendingIntent permissionIntent;
    TextView filePath;
    boolean isPrinterConnected;
    private static final int MESSAGE_REFRESH = 101;
    Button  linefeed;
    TextView connect_btn;
    TextView btn_disconnect;
    Button enableBlutooth;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();


    public String font_Size_38(String paramString)
    {
        return String.valueOf(new String(new byte[] { 27, 75, 0 })) + paramString;
    }


    String result;
    HouseDb db;
    double item_total = 0, t = 0;
    Spinner States,districts,Panchayat;
    Button save1,print;
    TextView date,time1,bill_no_bd,generate,Add;
    DBHelper mydb;
    List<Integer> getIntegerArray,getIntegerArray1;
    private ArrayAdapter<Integer> itemAdapter1;
    private ArrayAdapter<String> itemAdapter;
    ArrayList<String> array_item = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter<String> arrayAdapter1;
    private ArrayAdapter<String> arrayAdapter2;
    ArrayList<String> array_list = new ArrayList<String>();
    ArrayList<String> array_list1 = new ArrayList<String>();
    ArrayList<String> array_list2 = new ArrayList<String>();
    EditText HouseIncharge,MobileNum,Amount,partially_paid;
    String arr[]={"Andhra Pradesh"};
    String arr1[]={"Krishna"};
    int dyear, dmonth, dday;
    ImageView doornu,doornu1,doornu2,cancle,cancle1,cancle2;
    String time = "hh:mm aaa";

    EditText House;
 // Switch save;
    int bill_no;
    double gt, dt, st;
    String gw, gr, dw, dr, sw, sr;
    SQLiteDatabase data;
    Customerlistdb myDB;
    DBHelperreports mydbhelper;
    RadioButton hom,comm,small,large;

    DatabaseHandler databaseConnector;
    Context context;

    ImportExportExcel importExportExcel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wastmanagement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.calon);
        toolbar.setTitle("  Waste Management");
        setSupportActionBar(toolbar);
        mydb = new DBHelper(this);
        myDB=new Customerlistdb(this);
        array_list = mydb.getAllCotacts();
        array_list1 = mydb.getAllCotacts1();
        array_list2 = mydb.getAllCotacts2();
        db = new HouseDb(this);
        array_item = db.getAllCotacts();
        context=this;
        databaseConnector=new DatabaseHandler(context);
        importExportExcel=new ImportExportExcel(context);
        States=(Spinner)findViewById(R.id.spinner1);
        doornu1=(ImageView)findViewById(R.id.doornum1);
        doornu=(ImageView)findViewById(R.id.doornum2);
        doornu2=(ImageView)findViewById(R.id.doornum3);
        hom=(RadioButton)findViewById(R.id.radiohouse);
        comm=(RadioButton)findViewById(R.id.radiocommercial);
        small=(RadioButton)findViewById(R.id.radiosmall);
        large=(RadioButton)findViewById(R.id.radiolarge);

        cancle=(ImageView)findViewById(R.id.cancle3);
       // radiogroup = (RadioGroup) findViewById(R.id.payment_bd);
       // save=(Switch) findViewById(R.id.savesw);
        districts=(Spinner)findViewById(R.id.spinnerdistricts);
        Panchayat=(Spinner)findViewById(R.id.panchayat);
        House=(EditText)findViewById(R.id.doornum);
        date=(TextView)findViewById(R.id.Dateate);
        time1=(TextView)findViewById(R.id.time) ;
        HouseIncharge=(EditText)findViewById(R.id.landmark);
        MobileNum=(EditText)findViewById(R.id.mobilenum);
        Amount=(EditText)findViewById(R.id.amount);
        save1=(Button)findViewById(R.id.Save) ;
        generate = (TextView) findViewById(R.id.generatebill);
        //  tv = (TextView) findViewById(R.id.data);
        Add = (TextView) findViewById(R.id.add);
        //print=(Button)findViewById(R.id.Print);
        final Calendar c = Calendar.getInstance();
        dyear = c.get(Calendar.YEAR);
        dmonth = c.get(Calendar.MONTH);
        dday = c.get(Calendar.DAY_OF_MONTH);
        updateDepositDateDisplay();

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, array_list);
        States.setAdapter(arrayAdapter);
        arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, array_list1);
        districts.setAdapter(arrayAdapter1);
        arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, array_list2);
        Panchayat.setAdapter(arrayAdapter2);
       // itemAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, array_item);
       // House.setThreshold(1);
       // House.setAdapter(itemAdapter);


        sw = sr = dw = dr = gw = gr = "0";
        st = dt = gt = 0;
        bill_no_bd = (TextView) findViewById(R.id.bill_no_bd);
        mydbhelper = new DBHelperreports(MainActivity.this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);

        data = mydbhelper.getWritableDatabase();

        Cursor c_count = data.rawQuery("select * from " + Database.BI_TABLE_NAME, null);
        bill_no = c_count.getCount();
        bill_no++;
        bill_no_bd.setText(bill_no + "");

        Add.setEnabled(true);
        generate.setEnabled(false);
        item_total = 0;
        t = 0;


        hom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                small.setVisibility(View.INVISIBLE);
                large.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Selected Home",Toast.LENGTH_SHORT).show();
            }
        });
        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                small.setVisibility(View.VISIBLE);
                large.setVisibility(View.VISIBLE);
                //hom.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Selected Commercial",Toast.LENGTH_SHORT).show();
            }
        });

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( House.length() == 0 && HouseIncharge.length() == 0 && MobileNum.length() == 0 && Amount.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Please Enter the Full Details", Toast.LENGTH_SHORT).show();

                }
                else {
                    String editText1 = House.getText().toString();
                    String editText2 = HouseIncharge.getText().toString();
                    String editText3 = MobileNum.getText().toString();
                    result = db.insertContact(editText1,editText2,editText3);
                    Log.d(getClass().getName(), "value = " + result);
                    String pan = Panchayat.getSelectedItem().toString();
                    String hou = House.getText().toString();
                    String datea=date.getText().toString();
                    String timea=time1.getText().toString();
                    String hin = HouseIncharge.getText().toString();
                    String mob = MobileNum.getText().toString();
                    String amo = Amount.getText().toString();
                    String sta = States.getSelectedItem().toString();
                    String dis = districts.getSelectedItem().toString();
                    String Store = "";
                    Store += "\r\n States\t";
                    Store += "Districts\t";
                    Store += "Panchayat\t";
                    Store += "House Num\t";
                    Store += "House Incharge\t";
                    Store += "Mobile Num\t";
                    Store += "Amount\t";
                    Store +="Date\t";
                    Store +="Time\t";
                    Store += "\r\n" + sta;
                    Store += "\t" + dis;
                    Store += "\t" + pan;
                    Store += "\t" + hou;
                    Store += "\t" + hin;
                    Store += "\t" + mob;
                    Store += "\t" + amo;
                    Store += "\t" + datea;
                    Store += "\t" + timea;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                    Date now = new Date();
                    String fileName1 = formatter.format(now) + ".xls";
                    try {
                        File root = new File(Environment.getExternalStorageDirectory() + File.separator + "Cal_on Wast_Management", " Day Out Time Report ");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File gpxfile = new File(root, fileName1);
                        FileWriter writer = new FileWriter(gpxfile, true);
                        writer.append(Store + "\n\n");
                        writer.flush();
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    String fileName =  hin+ ".xls";


                    try {
                        File root = new File(Environment.getExternalStorageDirectory() + File.separator + "Cal_on Wast_Management", "Customer Report ");

                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File gpxfile = new File(root, fileName);


                        FileWriter writer = new FileWriter(gpxfile, true);
                        writer.append(Store + "\n\n");
                        writer.flush();
                        writer.close();


                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy_MM_dd");
                    Date now1 = new Date();
                    String fileName11 = formatter1.format(now1) + ".xls";
                    String Store1 = "";
                    String common0="";
                    String common1="";
                    String common2="";
                    String common3="";
                    String common4="";
                    String common5="";
                    String common6="";
                    String common7="";
                    Store1 += "\r\n" + sta;
                    Store1 += "\t  " + dis;
                    Store1 += "\n  " + pan;
                    Store1 += "\t  " + hou;
                    Store1 += "\n  " + hin;
                    Store1 += "\t  " + mob;
                    Store1 += "\n  " + amo;
                    Store1 += "\t  " + datea;
                    Store1 += "\n  " + timea;
                    common0=sta.concat("      "+dis);
                    common1=common1.concat(""+common0);
                    String str="\n"+common1;
                    common2=pan.concat("      "+hou);
                    common3=common3.concat(""+common2);
                    String str41=str.concat("\n"+common3);
                    common4=hin.concat("      "+mob);
                    common5=common5.concat(""+common4);
                    String str414=str41.concat("\n"+common5);
                    common6=amo.concat("      "+datea);
                    common7=common7.concat(""+common6);
                    String str4141=str414.concat("\n"+common7);
                   // generate.setText(""+str4141);

                    try {
                        File root1 = new File(Environment.getExternalStorageDirectory() + File.separator + "","");

                        if (!root1.exists()) {
                            root1.mkdirs();
                        }
                        File gpxfile1 = new File(root1, fileName11);
                        FileWriter writer1 = new FileWriter(gpxfile1, true);
                        writer1.append(str4141+ "\n");
                        writer1.flush();
                        writer1.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    try {
                        String heading="       Waste Management     ";
                        String xh=font_Size_38(heading);
                        String store1="Panchayat      :";
                        String str15=store1;
                        String store2="Date           :";
                        String str11=store2;
                        String store="Time           :";
                        String str12=store;
                        String store3="House Number   :";
                        String str19 = store3;
                        String store4="House Incharge :";
                        String str18 = store4;
                        String store5="Amount         :";
                        String str10 = store5;
                        String store111="Bill.No        :";
                        String k100=store111;
                        String pan1 = Panchayat.getSelectedItem().toString();
                        String str5=pan1;
                        String billnop=bill_no_bd.getText().toString();
                        String bill23=billnop;
                        String state=States.getSelectedItem().toString();
                        String distk=districts.getSelectedItem().toString();
                        String z=state.concat(" - "+distk);
                        String x=font_Size_38(z);
                            String hou1 = House.getText().toString();
                            String str9 = hou1;
                            String datea1=date.getText().toString();
                            String str1=datea1;
                            String timea1=time1.getText().toString();
                            String str2=timea1;
                            String mob1 = MobileNum.getText().toString();
                            String str6 = font_Size_38(mob1);
                            String amo1 = Amount.getText().toString();
                            String str0 =amo1;
                            String sta1 = States.getSelectedItem().toString();
                            String str3 = font_Size_38(sta1);
                            String dis1 = districts.getSelectedItem().toString();
                            String str4 = font_Size_38(dis1);
                            String str7 = mDumpTextView.getText().toString();
                            String str8 = str7;

                        String a00=k100.concat(bill23);
                        String k00=font_Size_38(a00);
                        String a=str15.concat(str5);
                        String k=font_Size_38(a);
                        String b=str1.concat("      "+str2);
                        String l=font_Size_38(b);
                        String d=str19.concat(str9);
                        String n=font_Size_38(d);
                        String e=str18.concat(str8);
                        String o=font_Size_38(e);
                        String f=str10.concat(str0);
                        String p=font_Size_38(f);
                        uConn.printData(xh);
                        uConn.printData(x);
                        uConn.printData(k);
                        uConn.printData(l);
                        uConn.printData(k00);
                        uConn.printData(n);
                        uConn.printData(o);
                        uConn.printData(p);


                    }
                    catch (Exception localException)
                    {
                        localException.printStackTrace();

                    }
                    addItem();
                    generateBill();

                    String mob1 = MobileNum.getText().toString();//mobile Number
                    String stat1=States.getSelectedItem().toString();
                    String dist1=districts.getSelectedItem().toString();
                    String sd1=stat1.concat("    "+dist1);
                    String panc1=Panchayat.getSelectedItem().toString();
                    String sdp1=sd1.concat("\n"+panc1);
                    String bill1="Bill No   :";
                    String bill2=bill_no_bd.getText().toString();
                    String bill3=bill1.concat(" "+bill2);
                    String add31=sdp1.concat("\n"+bill3);
                    String date1=date.getText().toString();
                    String timek11=time1.getText().toString();
                    String dt1=date1.concat("    "+timek11);
                    String add41=add31.concat("\n"+dt1);
                    String hon2="House No :";
                    String hon1=House.getText().toString();
                    String hon3=hon2.concat("  "+hon1);
                    String add51=add41.concat("\n"+hon3);
                    String hon8="House Incharge :";
                    String hon9=mDumpTextView.getText().toString();
                    String hon10=hon8.concat("  "+hon9);
                    String add61=add51.concat("\n"+hon10);
                    String hon21="Amount :";
                    String hon22=Amount.getText().toString();
                    String hon33=hon21.concat("  "+hon22);
                    String message=add61.concat("\n"+hon33);//message
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(""+buildRequestString(mob1, message), new AsyncHttpResponseHandler() {

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                            Toast.makeText(MainActivity.this, "SuccessFully Completed", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                            Toast.makeText(MainActivity.this, "NetWork Fail", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onRetry(int retryNo) {

                            Toast.makeText(MainActivity.this, "Please On The Data Connection", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                String Custname = HouseIncharge.getText().toString();
                String Custarea = House.getText().toString();
                String Custnum = MobileNum.getText().toString();


                if(Custname.length()!= 0 && Custarea.length() != 0 && Custnum.length() != 0){
                    AddData(Custname,Custarea,Custnum);
                    mydbhelper = new DBHelperreports(MainActivity.this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
                    data = mydbhelper.getWritableDatabase();
                    Cursor c_count = data.rawQuery("select * from " + Database.BI_TABLE_NAME, null);
                    bill_no = c_count.getCount();
                    bill_no++;
                    bill_no_bd.setText(bill_no + "");
                    House.setText("");
                    HouseIncharge.setText("");
                    MobileNum.setText("");
                    Amount.setText("");


                }else{
                    Toast.makeText(MainActivity.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }




            }
        });
        doornu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty=HouseIncharge.getText().toString();
                myDB=new Customerlistdb(getApplicationContext());
                data=myDB.getReadableDatabase();
                Cursor cursor=myDB.getdata(empty,data);
                if (cursor.moveToFirst()){
                    String Village=cursor.getString(0);
                    String mobilenum=cursor.getString(1);


                    House.setText(Village);
                    MobileNum.setText(mobilenum);
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HouseIncharge.setText("");
                House.setText("");
                MobileNum.setText("");
                Toast.makeText(getApplicationContext(),"Data Was Cleared",Toast.LENGTH_SHORT).show();
            }
        });
        doornu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty=House.getText().toString();
                myDB=new Customerlistdb(getApplicationContext());
                data=myDB.getReadableDatabase();
                Cursor cursor=myDB.getdata1(empty,data);
                if (cursor.moveToFirst()){
                    String Village=cursor.getString(0);
                    String mobilenum=cursor.getString(1);
                    HouseIncharge.setText(Village);
                    MobileNum.setText(mobilenum);
                    Toast.makeText(getApplicationContext(),"Data Was Inserted",Toast.LENGTH_SHORT).show();
                }
            }
        });

        doornu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty=MobileNum.getText().toString();
                myDB=new Customerlistdb(getApplicationContext());
                data=myDB.getReadableDatabase();
                Cursor cursor=myDB.getdata2(empty,data);
                if (cursor.moveToFirst()){
                    String Village=cursor.getString(0);
                    String mobilenum=cursor.getString(1);
                    HouseIncharge.setText(Village);
                    House.setText(mobilenum);
                    Toast.makeText(getApplicationContext(),"Data Was Inserted",Toast.LENGTH_SHORT).show();
                }
            }
        });

        blueEnable=(LinearLayout) findViewById(R.id.blueEnable);
        blueDisable=(LinearLayout) findViewById(R.id.blueDisable);
        mDumpTextView = (EditText) findViewById(R.id.landmark);
        mScrollView = (ScrollView) findViewById(R.id.demoScrollerusb);
        bluetooth_devices = (Spinner) findViewById(R.id.spinner_baudrate);

        TextView save = (TextView) findViewById(R.id.tv_save_usb);





        connect_btn = (TextView) findViewById(R.id.btn_connect);
        btn_disconnect = (TextView) findViewById(R.id.btn_disconnect);
        enableBlutooth = (Button) findViewById(R.id.enableBlutooth);
        enableBlutooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 1000);
            }
        });
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uConn.closeBT();
                    showDisonnected();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

        if (mBluetoothAdapter == null) {
            showUnsupported();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                showDisabled();
            } else {
                showEnabled();

                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices != null) {
                    mDeviceList.addAll(pairedDevices);

                    updateDeviceList();
                }
            }

            mProgressDlg = new ProgressDialog(this);

            mProgressDlg.setMessage("Scanning...");
            mProgressDlg.setCancelable(false);
            mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    mBluetoothAdapter.cancelDiscovery();
                }
            });

            mConnectingDlg = new ProgressDialog(this);

            mConnectingDlg.setMessage("Connecting...");
            mConnectingDlg.setCancelable(false);

            mConnector = new P25Connector(new P25Connector.P25ConnectionListener() {

                @Override
                public void onStartConnecting() {
                    mConnectingDlg.show();
                }

                @Override
                public void onConnectionSuccess() {
                    mConnectingDlg.dismiss();


                }

                @Override
                public void onConnectionFailed(String error) {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onConnectionCancelled() {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onDisconnected() {
                    showDisonnected();
                }
            });
        }
        uConn = new BTConnection();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    filePath.setText(exporttoCSV(mDumpTextView.getText().toString()));
                    mDumpTextView.setText("");
                    Toast.makeText(getApplicationContext(), "CSV file created and saved in internal storage" + filePath.getText().toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Error Occurred while export file", Toast.LENGTH_SHORT).show();
                    filePath.setText("Error Occurred while export file");
                    e.printStackTrace();
                }
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bluetooth_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting :
            {
                Intent i=new Intent(getApplicationContext(),Settings.class);
                startActivity(i);
                return true;
            }
            case R.id.report :
            {
                Intent i=new Intent(getApplicationContext(),Reports.class);
                startActivity(i);
                return true;
            }
            case R.id.dayreport :
            {
                Intent i=new Intent(getApplicationContext(),DayReports.class);
                startActivity(i);
                return true;
            }
            case R.id.fetch :
            {
                Intent i=new Intent(getApplicationContext(),ImportExportActivity.class);
                startActivity(i);
                return true;
            }
            case R.id.CustomerReport :
            {
                Intent i=new Intent(getApplicationContext(),Customersample.class);
                startActivity(i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateDepositDateDisplay() {

        date.setText(String.format("%02d-%02d-%04d", dday, dmonth + 1, dyear));
        java.util.Date noteTS = Calendar.getInstance().getTime();
        time1.setText(DateFormat.format(time, noteTS));
    }



    private static String buildRequestString(String mob1, String message){

        String encoded_message= URLEncoder.encode(message);
        String url="http://35.154.195.48:4300/sms?";
        String authkey="892YVXYLYR";
        StringBuilder sbPostData= new StringBuilder(url);
        sbPostData.append("authkey="+authkey);
        sbPostData.append("&mNo="+mob1);
        sbPostData.append("&msg="+encoded_message);
       // Log.d(" ",+sbPostData.toString());
        return sbPostData.toString();
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
            {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    showEnabled();
                } else if (state == BluetoothAdapter.STATE_OFF) {
                    showDisabled();
                }
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();

                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();

                updateDeviceList();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                mDeviceList.add(device);

                showToast("Found device " + device.getName());
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED) {
                    showToast("Paired");

                    connect();
                }
            }
        }
    };
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private String[] getArray(ArrayList<BluetoothDevice> data) {
        String[] list = new String[0];

        if (data == null) return list;

        int size = data.size();
        list = new String[size];

        for (int i = 0; i < size; i++) {
            list[i] = data.get(i).getName();
        }

        return list;
    }
    private void updateDeviceList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, getArray(mDeviceList));

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        bluetooth_devices.setAdapter(adapter);
        bluetooth_devices.setSelection(0);
    }

    private void showDisabled() {
        showToast("Bluetooth disabled");

        blueEnable.setVisibility(View.GONE);
        blueDisable.setVisibility(View.VISIBLE);
    }

    private void showEnabled() {
        showToast("Bluetooth enabled");
        blueEnable.setVisibility(View.VISIBLE);
        blueDisable.setVisibility(View.GONE);
    }
    private void showConnected() {
        showToast("Connected");
        isPrinterConnected=true;
        connect_btn.setVisibility(View.GONE);
        btn_disconnect.setVisibility(View.VISIBLE);
        enableBlutooth.setVisibility(View.GONE);
        bluetooth_devices.setEnabled(false);
    }

    private void showDisonnected() {
        showToast("Disconnected");
        connect_btn.setVisibility(View.VISIBLE);
        btn_disconnect.setVisibility(View.GONE);
        enableBlutooth.setVisibility(View.GONE);
        bluetooth_devices.setEnabled(true);
    }
    private void showUnsupported() {
        showToast("Bluetooth is unsupported by this device");

        connect_btn.setEnabled(false);
        bluetooth_devices.setEnabled(false);
    }

    public  void createBond(BluetoothDevice device) throws Exception {
        BTAddress = device.toString();
        BTAddress.trim();
        try {
            uConn.openBT(BTAddress);
            Log.e("coonected", "bt connected2");
            Log.e("coonected", BTAddress);
        } catch (IOException e) {

            e.printStackTrace();
        }
        try {
            Class<?> cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class<?>[] par = {};

            Method method = cl.getMethod("createBond", par);

            method.invoke(device);

        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }
    }

    private void connect() {
        if (mDeviceList == null || mDeviceList.size() == 0) {
            return;
        }

        BluetoothDevice device = mDeviceList.get(bluetooth_devices.getSelectedItemPosition());

        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            try {
                uConn.openBT(device.getAddress());
                registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
                createBond(device);
            } catch (Exception e) {
                showToast("Failed to pair device");

                return;
            }
        }

        try {
            if (!blueToothconnected) {
                // mConnector.connect(device);
                Log.e("coonected", "bt connected2");
                uConn.openBT(device.getAddress());
                Log.e("blueToothconnected", String.valueOf(blueToothconnected));
                showConnected();
                blueToothconnected = true;
            } else {
                Log.e("blueToothconnected", String.valueOf(blueToothconnected));
                BTConnection.closeBT();
                blueToothconnected = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onResume() {
        super.onResume();

    }
    public static void showBluetoothData( byte[] data) {
        String s=new String(data);
        totalData=totalData+s;
        mDumpTextView.append(s);
        mScrollView.smoothScrollTo(0, mScrollView.getBottom());

    }
    public String exporttoCSV(final String totalData) throws IOException {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23
                );
            }
        }
        File mainFolder = new File(Environment.getExternalStorageDirectory()
                + "/Cal-ONTerminal");
        boolean mainVar = false;
        if (!mainFolder.exists())
            mainVar=mainFolder.mkdir();
        final File folder = new File(Environment.getExternalStorageDirectory()
                + "/Cal-ONTerminal/Bluetooth_terminal_files");

        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();

        System.out.println("" + var);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd_HHmmss");
        Date dt = new Date();
        final String fileFormatName = fmt.format(dt) + ".csv";
        final String filename = folder.toString() + "/" + fileFormatName;

        new Thread() {
            public void run() {
                try {

                    FileWriter fw = new FileWriter(filename);
                    fw.append(totalData);
                    fw.close();

                } catch (Exception e) {
                }
            }
        }.start();
        return "  [/Cal-ONTerminal/Bluetooth_terminal_files/" + fileFormatName + "]";
    }
    public void generateBill() {
        mydbhelper = new DBHelperreports(this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
        data = mydbhelper.getWritableDatabase();

        String col[] = new String[]{Database.C_NAME, Database.C_ADV};
        Cursor c = data.query(Database.C_TABLE_NAME, col, null, null, null, null, null);
        c.moveToFirst();
        double d = 0;
        while (!c.isAfterLast()) {
            if (c.getString(0).equals(HouseIncharge.getText().toString())) {
                d = Double.parseDouble(c.getString(1));
            }
            c.moveToNext();
        }

        ContentValues cs = new ContentValues();
        cs.put(Database.C_ADV, d + "");
        data.update(Database.C_TABLE_NAME, cs, Database.C_NAME + " = '" + HouseIncharge.getText().toString() + "'", null);
        ContentValues cv = new ContentValues();
        cv.put(Database.BI_BILL_NO, bill_no + "");
        cv.put(Database.BI_BILL_NAME, HouseIncharge.getText().toString());
        cv.put(Database.BI_BILL_DATE, date.getText().toString());
        cv.put(Database.BI_BILL_TOTAL, t+"");
        cv.put(Database.BI_PAYMENT_STATUS, House.getText().toString());
        cv.put(Database.BI_AMT_PAID, MobileNum.getText().toString());
        cv.put(Database.BI_AMT_PENDING,Panchayat.getSelectedItem().toString());
        cv.put(Database.BI_DESC ,Amount.getText().toString());
        long r = data.insertOrThrow(Database.BI_TABLE_NAME, null, cv);
        mydbhelper.close();


    }






    public void addItem() {
        mydbhelper = new DBHelperreports(this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
            data = mydbhelper.getWritableDatabase();
           ContentValues cv = new ContentValues();
            cv.put(Database.BD_BILL_NO, bill_no);
            cv.put(Database.BD_NAME, HouseIncharge.getText().toString());
            cv.put(Database.BD_DATE, date.getText().toString());
            cv.put(Database.BD_ITEM_ID,House.getText().toString());
            cv.put(Database.BD_PARTICULARS, States.getSelectedItem().toString());
            cv.put(Database.BD_SILVER_WT,Amount.getText().toString());
            cv.put(Database.BD_LABOUR_CHARGES, districts.getSelectedItem().toString());
            cv.put(Database.BD_ITEM_TOTAL,MobileNum.getText().toString());
            cv.put(Database.BD_GOLD_WT,Panchayat.getSelectedItem().toString());
            long r = data.insertOrThrow(Database.BD_TABLE_NAME, null, cv);
             mydbhelper.close();

        }

    public void AddData( String Custname, String Custarea, String Custnum ) {

        boolean insertData = myDB.addData(Custname,Custarea,Custnum);

        if(insertData==true){

        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }
}
