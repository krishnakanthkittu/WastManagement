package cal_on.wastmanagement;

import java.util.ArrayList;

public class WorldReport {
    private String name;
    private String date;
    private String bill_no;
    private String Amount;
    private  String amt_paid;
    private  String total_amt;
    private  String status;

    public WorldReport(String name, String date, String bill_no,String Amount,String amt_paid,String total_amt, String status) {
        this.name = name;
        this.date = date;
        this.bill_no = bill_no;
        this.Amount=Amount;
        this.amt_paid=amt_paid;
        this.total_amt=total_amt;
        this.status=status;
    }




    public String getRank() {

        return this.name;
    }

    public String getCountry() {
        return this.date;
    }

    public String getPopulation() {
        return this.bill_no;
    }
    public String getAmount() {
        return this.Amount;
    }
    public String getAmt_paid() {
        return this.amt_paid;
    }
    public String getTotal_amt() {
        return this.total_amt;
    }

    public String getStatus() {
        return status;
    }
}
