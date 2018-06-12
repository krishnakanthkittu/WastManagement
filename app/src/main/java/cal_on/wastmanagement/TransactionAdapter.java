package cal_on.wastmanagement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransactionAdapter extends BaseAdapter {
	String[]name,date,bill_no,item_no,Amount,total_amt,amt_paid,status;
	LayoutInflater inflater;
	private Context c;
	TransactionAdapter(Context c, String[] name, String[] date, String[] bill_no,  String[] Amount,
					   String[] amt_paid, String[] total_amt,String[] status){
		this.c=c;
		this.name=name;
		this.date=date;
		this.bill_no=bill_no;
		this.item_no=item_no;
		this.Amount=Amount;
		this.total_amt=total_amt;
		this.amt_paid=amt_paid;
		this.status=status;

		inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bill_no.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int a, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView trname,trdate,trbillno,trbilltotal,trbillitem,trtotalamt,tramtpaid,trstatus;
		View r;
		r = inflater.inflate(R.layout.reporttransactions_inflate, null);
		trname=(TextView)r.findViewById(R.id.name_tr);
		trdate=(TextView)r.findViewById(R.id.date_tr);
		trbillno=(TextView)r.findViewById(R.id.bill_no_tr);
		trbilltotal=(TextView)r.findViewById(R.id.bill_total_tr);
		trtotalamt=(TextView)r.findViewById(R.id.total_amt_tr);
		tramtpaid=(TextView)r.findViewById(R.id.amt_paid_tr);
		trstatus=(TextView)r.findViewById(R.id.housenum) ;
		trname.setText(name[a]);
		trdate.setText(date[a]);
		trbillno.setText(bill_no[a]);
		trbilltotal.setText(total_amt[a]);
		trstatus.setText(status[a]);
		trtotalamt.setText(Amount[a]);
		tramtpaid.setText(amt_paid[a]);




		return r;
	}

}
