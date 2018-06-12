package cal_on.wastmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Cal_on on 9/26/2017.
 */

public class ReportAdapter  extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<WorldReport> worldpopulationlist = null;
    private ArrayList<WorldReport> arraylist;

    public ReportAdapter(Context context, List<WorldReport> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<WorldReport>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView rank;
        TextView country;
        TextView population;
        TextView Amount;
        TextView Panchayat;
        TextView mobile;
        TextView status;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public WorldReport getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.transactions_inflate, null);
            // Locate the TextViews in listview_item.xml
            holder.rank = (TextView) view.findViewById(R.id.bill_no_tr);
            holder.country = (TextView) view.findViewById(R.id.date_tr);
            holder.population = (TextView) view.findViewById(R.id.name_tr);
            holder.Amount = (TextView) view.findViewById(R.id.total_amt_tr);
            holder.Panchayat = (TextView) view.findViewById(R.id.bill_total_tr);
            holder.mobile = (TextView) view.findViewById(R.id.amt_paid_tr);
            holder.status = (TextView) view.findViewById(R.id.housenum);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.rank.setText(worldpopulationlist.get(position).getRank());
        holder.country.setText(worldpopulationlist.get(position).getCountry());
        holder.population.setText(worldpopulationlist.get(position).getPopulation());
        holder.Amount.setText(worldpopulationlist.get(position).getAmount());
        holder.Panchayat.setText(worldpopulationlist.get(position).getAmt_paid());
        holder.mobile.setText(worldpopulationlist.get(position).getTotal_amt());
        holder.status.setText(worldpopulationlist.get(position).getStatus());

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, BillSummaryAdapter.class);

                intent.putExtra("rank",(worldpopulationlist.get(position).getRank()));

                intent.putExtra("country",(worldpopulationlist.get(position).getCountry()));

                intent.putExtra("population",(worldpopulationlist.get(position).getPopulation()));
                intent.putExtra("amount",(worldpopulationlist.get(position).getAmount()));
                intent.putExtra("mobile",(worldpopulationlist.get(position).getAmt_paid()));
                intent.putExtra("panchi",(worldpopulationlist.get(position).getTotal_amt()));
                intent.putExtra("house num",(worldpopulationlist.get(position).getStatus()));

                mContext.startActivity(intent);
            }
        });


        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (WorldReport wp : arraylist)
            {
                if (wp.getRank().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
