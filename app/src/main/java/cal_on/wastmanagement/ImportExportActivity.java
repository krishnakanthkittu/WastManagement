package cal_on.wastmanagement;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImportExportActivity extends Activity implements OnClickListener {

	DatabaseHandler databaseConnector;
	Context context;
	Button importBtn,exportBtn,view;
	ImportExportExcel importExportExcel;
	EditText id,nume,age;
    SQLiteDatabase data;
    DatebaseHelper mydb;
	String exportFileName="ExportExcel.csv";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import_export);
        initializeUI();
        view=(Button)findViewById(R.id.View);
        id=(EditText)findViewById(R.id.id_num);
        nume=(EditText)findViewById(R.id.Age);
        age=(EditText)findViewById(R.id.name);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.length() == 0){

                    Toast.makeText(getApplicationContext(),"Please Enter the Id of Customer", Toast.LENGTH_SHORT).show();

                }
                else {
                    int Empid = Integer.parseInt(id.getText().toString());


                    data = openOrCreateDatabase("userinfo", 0, null);


                    String table = "create table if not exists user(Eid Integer primary key autoincrement," +
                            "username varchar(20),age varchar2(20) )";



             //       String create_table = "create table " + user"(id integer primary key autoincrement," + USER_NAME + " text,"
                        //    + USER_AGE +" text);";
                    data.execSQL(table);

                    String where = "Eid=" + Empid;

                    Cursor c = data.query("user", null, where, null, null, null, null);


                    while (c.moveToNext())

                    {

                        nume.setText(c.getString(1));
                        age.setText(c.getString(2));
                      //  mobile.setText(c.getString(3));


                    }
                }

            }
        });

	}

	public void initializeUI()
	{
		context=this;
		databaseConnector=new DatabaseHandler(context);
		importExportExcel=new ImportExportExcel(context);
		importBtn=(Button) findViewById(R.id.importBtn);
		exportBtn=(Button) findViewById(R.id.export);

		
		importBtn.setOnClickListener(this);
		exportBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.importBtn:
			try {
				importExportExcel.importDataFromCSV();
				Toast.makeText(context, "File imported successfully", Toast.LENGTH_LONG).show();
			}
			catch (Exception e){
				Toast.makeText(context, "File Is Not Existed", Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.export:
			try {
				importExportExcel.exportDataToCSV(exportFileName);
				Toast.makeText(context, "File exported successfully", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(context, "Please,Import data first", Toast.LENGTH_LONG).show();
			
			}
			break;
		default:
			break;
		}
	}

}
