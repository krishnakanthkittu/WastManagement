package cal_on.wastmanagement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import cal_on.wastmanagement.NewDatabase.DBHelperreports;
import cal_on.wastmanagement.NewDatabase.Database;

/**
 * Created by Cal_on on 9/21/2017.
 */

public class CustomerBillSummary extends Activity {

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

    static String imagesDir = Environment.getExternalStorageDirectory().getPath();
    String capturedImgSave;
    LinearLayout blueDisable;
    LinearLayout blueEnable;


    private static EditText mDumpTextView,heighted;
    private static ScrollView mScrollView;
    private static   String totalData = "";
    private PendingIntent permissionIntent;
    TextView filePath;
    boolean isPrinterConnected;
    private static final int MESSAGE_REFRESH = 101;

    TextView connect_btn;
    TextView btn_disconnect;
    Button enableBlutooth;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();


    public String font_Size_38(String paramString)
    {
        return String.valueOf(new String(new byte[] { 27, 75, 9 })) + paramString;
    }

    TextView b_name,b_no,b_date, bill_total,amt_total,amt_paid;
    Button linefeed;
    SQLiteDatabase data;
    int count;
    BTConnection uConn;
    String bill_name,bill_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_summary);
        Intent in=getIntent();

        bill_name=in.getStringExtra("Bill_Name");
        ///bill_t=in.getStringExtra("bill_total");

        DBHelperreports mydbhelper=new DBHelperreports(this, Database.CAl_DATABASE_NAME, null, Database.CAL_DATABASE_VERSION);
        data=mydbhelper.getWritableDatabase();
        Cursor c_count= data.rawQuery("select * from "+Database.BD_TABLE_NAME+" where "+Database.BD_NAME+" = "+bill_name,null);
        count=c_count.getCount();

        String itemname[]=new String[count];
        String labour_charges[]=new String[count];
        String labour_charges1[]=new String[count];
        String item_no[]=new String[count];
        String item_total[]=new String[count];
        String item_singlecost[]=new String[count];
        String Amount[]=new String[count];
        String Date[]=new String[count];
        String BillNo[]=new String[count];

        String col1[]=new String[]{Database.BD_NAME,Database.BD_DATE,Database.BD_BILL_NO};
        Cursor c1=data.query(Database.BD_TABLE_NAME, col1, null, null, null, null, null);
        for(c1.moveToFirst();!c1.isAfterLast();c1.moveToNext()) {
            if (c1.getString(0).equals(bill_name)) {
                break;

            }

        }
        SQLiteDatabase mydb;
        mydb=mydbhelper.getWritableDatabase();
        String col2[]=new String[]{Database.BI_BILL_NO,Database.BI_BILL_TOTAL,Database.BI_AMT_PAID,Database.BI_AMT_PENDING,Database.BI_PAYMENT_STATUS,Database.BI_BILL_DATE,Database.BI_BILL_NAME};
        Cursor c2=mydb.query(Database.BI_TABLE_NAME, col2, null, null, null, null, null);
        for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext()){
            if(c2.getString(6).equals(bill_name)){
                break;
            }
        }
        String col[]=new String[]{Database.BD_PARTICULARS,Database.BD_LABOUR_CHARGES,Database.BD_ITEM_ID,Database.BD_ITEM_TOTAL,Database.BD_BILL_NO,Database.BD_GOLD_WT,Database.BD_NAME,Database.BD_SILVER_WT,Database.BD_DATE,Database.BD_BILL_NO};
        Cursor c=data.query(Database.BD_TABLE_NAME, col, null, null, null, null, null);
        int i=0;
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if(c.getString(8).equals(bill_name)){
                itemname[i]=c.getString(0);
                labour_charges[i]=c.getString(1);
                item_no[i]=c.getString(2);
                item_total[i]=c.getString(3);
                item_singlecost[i]=c.getString(5);
                labour_charges1[i]=c.getString(6);
                Amount[i]=c.getString(7);
                Date[i]=c.getString(8);
                BillNo[i]=c.getString(9);
                i++;
            }


        }


        data.close();
        ListView l=(ListView)findViewById(R.id.listView1);
     //   l.setAdapter(new BillSummaryAdapter(this,c,itemname,labour_charges,item_no,labour_charges1,item_total,item_singlecost,Amount,Date,BillNo));


        blueEnable=(LinearLayout) findViewById(R.id.blueEnable);
        blueDisable=(LinearLayout) findViewById(R.id.blueDisable);
        mDumpTextView = (EditText) findViewById(R.id.landmark);
        mScrollView = (ScrollView) findViewById(R.id.demoScrollerusb);


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

        //   bluetooth_devices.setAdapter(adapter);
        //  bluetooth_devices.setSelection(0);
    }

    private void showDisabled() {
        showToast("Bluetooth disabled");

        blueEnable.setVisibility(View.GONE);
        blueDisable.setVisibility(View.VISIBLE);
    }

    private void showEnabled() {
        // showToast("Bluetooth enabled");
        blueEnable.setVisibility(View.VISIBLE);
        blueDisable.setVisibility(View.GONE);
    }
    private void showConnected() {
        showToast("Connected");
        isPrinterConnected=true;
        connect_btn.setVisibility(View.GONE);
        btn_disconnect.setVisibility(View.VISIBLE);
        enableBlutooth.setVisibility(View.GONE);
        // bluetooth_devices.setEnabled(false);
    }

    private void showDisonnected() {
        showToast("Disconnected");
        connect_btn.setVisibility(View.VISIBLE);
        btn_disconnect.setVisibility(View.GONE);
        enableBlutooth.setVisibility(View.GONE);
        //  bluetooth_devices.setEnabled(true);
    }
    private void showUnsupported() {
        showToast("Bluetooth is unsupported by this device");

        connect_btn.setEnabled(false);
        // bluetooth_devices.setEnabled(false);
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


}