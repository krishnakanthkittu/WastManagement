package cal_on.wastmanagement;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Cal_on on 8/7/2017.
 */

public class Settings extends Activity {
    EditText state,Districts,pan;
    Button save;
    String result;
    DBHelper db;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        db = new DBHelper(this);
        state=(EditText)findViewById(R.id.sate1);
        Districts=(EditText)findViewById(R.id.Districts1);
        pan=(EditText)findViewById(R.id.Panchayat1) ;
        save=(Button)findViewById(R.id.save1);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editText1 = state.getText().toString();
                String editText2 = Districts.getText().toString();
                String editText3 = pan.getText().toString();
                result = db.insertContact(editText1,editText2,editText3);
                Log.d(getClass().getName(), "value = " + result);
                if (state.length() != 0 && Districts.length() != 0 && pan.length() != 0) {

                        state.setText("");
                        Districts.setText("");
                        pan.setText("");
                    Toast.makeText(getApplicationContext()," Details  Successfully Stored",Toast.LENGTH_SHORT).show();
                    }
                else if (state.length() == 0 && Districts.length() == 0 && pan.length() == 0) {
                    Toast.makeText(getApplicationContext(),"Please Enter the Details",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext()," Details  Successfully Stored",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
