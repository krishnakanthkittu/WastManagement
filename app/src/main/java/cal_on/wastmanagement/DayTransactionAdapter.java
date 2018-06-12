package cal_on.wastmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Cal_on on 9/11/2017.
 */

public class DayTransactionAdapter extends BaseAdapter {
    String[]date;
    double total[];
    LayoutInflater inflater;
    private Context c;
    DayTransactionAdapter(Context c, String[] date,double[] total){
        this.c=c;

        this.date=date;
        this.total=total;


        inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return date.length;
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
        TextView trdate,trtotalamt;
        View r;
        r = inflater.inflate(R.layout.daytransactions_inflate, null);
        trdate=(TextView)r.findViewById(R.id.date_tr);
        trtotalamt=(TextView)r.findViewById(R.id.total_amt_tr);
        trdate.setText(date[a]);
        trtotalamt.setText(""+ total[a]);
        return r;
    }

}