package com.example.ankurshukla.hostel.Dashboard_Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ankurshukla.hostel.R;

public class Saved_Form extends AppCompatActivity {

    String [] sname = new String[]{"","","","","",""};
    String [] sid = new String[]{"","","","","",""};
    String number;
    String hostelid[] = new String[2];
    String floor[] = new String[2];
    LinearLayout sf1,sf2,sf3;
    TextView[] rname = new TextView[6];//r ==room and n=name
    TextView [] rollno = new TextView[6]; //r=room and id=rool no
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        sname = intent.getStringArrayExtra("sname");
        sid = intent.getStringArrayExtra("sid");
        number = intent.getStringExtra("noOfStudents");
        hostelid = intent.getStringArrayExtra("Hostel_type");//getting all hostel types for preferneces
        floor  = intent.getStringArrayExtra("Floor_type");//getting all floor types from preferences
        setContentView(R.layout.activity_saved__form);



        int n = Integer.parseInt(number);
        sf1 = (LinearLayout)findViewById(R.id.sfl1);
        sf2 = (LinearLayout)findViewById(R.id.sfl2);
        sf3 = (LinearLayout)findViewById(R.id.sfl3);
        rname[0] = (TextView) findViewById(R.id.savedfullname1rm1);
        rname[1] = (TextView) findViewById(R.id.savedfullname2rm1);
        rname[2] = (TextView) findViewById(R.id.savedfullname1rm2);
        rname[3] = (TextView) findViewById(R.id.savedfullname2rm2);
        rname[4] = (TextView) findViewById(R.id.savedfullname1rm3);
        rname[5] = (TextView) findViewById(R.id.savedfullname2rm3);
        rollno[0] = (TextView) findViewById(R.id.savedid1rm1);
        rollno[1] = (TextView) findViewById(R.id.savedid2rm1);
        rollno[2] = (TextView) findViewById(R.id.savedid1rm2);
        rollno[3] = (TextView) findViewById(R.id.savedid2rm2);
        rollno[4] = (TextView) findViewById(R.id.savedid1rm3);
        rollno[5] = (TextView) findViewById(R.id.savedid2rm3);
        submit = (Button) findViewById(R.id.sf_saved);

        if(number.equals("1")){
            sf2.setVisibility(View.GONE);
            sf3.setVisibility(View.GONE);
        }
        else if(number.equals("2")){
            sf3.setVisibility(View.GONE);
        }

        for(int i=0;i<2*n;i++){//setting the names and roll no from  wingform array to saved from edittext
            rname[i].setText(sname[i]);
            rollno[i].setText(sid[i]);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(Saved_Form.this,WingForm.class);
                i.putExtra("sname",sname);//passing all names
                i.putExtra("sid",sid);//passing all id
                i.putExtra("noOfStudents",number);//passing number of rooms
                i.putExtra("Hostel_type",hostelid);
                i.putExtra("Floor_type",floor);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved__form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
