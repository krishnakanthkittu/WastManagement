package cal_on.wastmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Cal_on on 9/27/2017.
 */

public class CustomerSummaryAdapter extends Activity {
    TextView txtrank;
    TextView txtcountry;
    TextView txtpopulation,sample,kittu,abbas,house;
    String bill_no;
    String name;
    String date;
    String Amount;
    String amt_paid;
    String total_amt;
    String status;
    Button linefeed;


    BTConnection uConn;
    public String font_Size_38(String paramString)
    {
        return String.valueOf(new String(new byte[] { 27, 75, 0 })) + paramString;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custbill_details_inflate);
        Intent i = getIntent();

        bill_no = i.getStringExtra("rank");

        name = i.getStringExtra("country");

        date = i.getStringExtra("population");
        Amount=i.getStringExtra("amount");
        amt_paid=i.getStringExtra("mobile");
        total_amt=i.getStringExtra("panchi");
        status=i.getStringExtra("house num");


        txtrank = (TextView) findViewById(R.id.particulars_bs);
        txtcountry = (TextView) findViewById(R.id.single);
        txtpopulation = (TextView) findViewById(R.id.labour_charges_bs);
        sample=(TextView) findViewById(R.id.datekkk);
        kittu=(TextView)findViewById(R.id.bill_no_bs) ;
        abbas=(TextView)findViewById(R.id.labour_charges_ds);
        house=(TextView)findViewById(R.id.labour_charges_Cs);

        txtrank.setText(bill_no);
        txtcountry.setText(name);
        txtpopulation.setText(date);
        sample.setText(Amount);
        kittu.setText(amt_paid);
        abbas.setText(total_amt);
        house.setText(status);
        String heading="       Waste Management     ";
        final String xh=font_Size_38(heading);

        String store5k="House Incharge   :";
        String str10k = store5k;
        String pan1 = txtrank.getText().toString();
        String str5=pan1;
        String a1=str10k.concat(str5);
        final String k=font_Size_38(a1);


        String store3="Bill No          :";
        String str19 = store3;
        String state=txtcountry.getText().toString();
        String str19k = state;
        String a2=str19.concat(str19k);
        final String k1=font_Size_38(a2);

        String store2="Date             :";
        String str11=store2;
        String state1=txtpopulation.getText().toString();
        String str19l = state1;
        String a3=str11.concat(str19l);
        final String k2=font_Size_38(a3);

        String store5="Amount           :";
        String str10 = store5;
        String state2=sample.getText().toString();
        String str19m = state2;
        String a4=str10.concat(str19m);
        final String k3=font_Size_38(a4);

        String store="Mobile Number    :";
        String str12=store;
        String state3=kittu.getText().toString();
        String str19n = state3;
        String a5=str12.concat(str19n);
        final String k4=font_Size_38(a5);


        String store1="Panchayat        :";
        String str15=store1;
        String state4=abbas.getText().toString();
        String str19o = state4;
        String a6=str15.concat(str19o);
        final String k5=font_Size_38(a6);



        String store12="House Number     :";
        String str16=store12;
        String state5=house.getText().toString();
        String str19p = state5;
        String a7=str16.concat(str19p);
        final String k6=font_Size_38(a7);


        linefeed = (Button)findViewById(R.id.Print);
        linefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    uConn.printData(xh);
                    uConn.printData(k);
                    uConn.printData(k1);
                    uConn.printData(k2);
                    uConn.printData(k3);
                    uConn.printData(k4);
                    uConn.printData(k5);
                    uConn.printData(k6);


                } catch (NullPointerException e) {

                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"NOt Printing",Toast.LENGTH_SHORT).show();
                }
            }
        });

        uConn = new BTConnection();

    }
}
