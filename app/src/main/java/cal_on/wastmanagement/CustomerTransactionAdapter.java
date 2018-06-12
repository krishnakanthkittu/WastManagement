package cal_on.wastmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Cal_on on 9/21/2017.
 */

public class CustomerTransactionAdapter extends BaseAdapter {
    String[]Name;
    double total[];
    LayoutInflater inflater;
    private Context c;
    CustomerTransactionAdapter(Context c, String[] Name){
        this.c=c;

        this.Name=Name;
      //  this.total=total;


        inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Name.length;
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
        TextView trdate;
        View r;
        r = inflater.inflate(R.layout.customertransactions_inflate, null);

        trdate=(TextView)r.findViewById(R.id.date_tr);

        trdate.setText(Name[a]);

        return r;
    }

}