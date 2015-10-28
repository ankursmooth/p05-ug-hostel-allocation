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

public class Submitted_Form extends AppCompatActivity {

    String [] sname = new String[]{"","","","","",""};
    String [] sid = new String[]{"","","","","",""};
    String number;
    String hostelid[] = new String[]{"",""};
    String floor[] = new String[]{"",""};
    LinearLayout submitf1,submitf2,submitf3;
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
        setContentView(R.layout.activity_submitted__form);

        int n = Integer.parseInt(number);
        submitf1 = (LinearLayout)findViewById(R.id.submitfl1);
        submitf2 = (LinearLayout)findViewById(R.id.submitfl2);
        submitf3 = (LinearLayout)findViewById(R.id.submitfl3);
        rname[0] = (TextView) findViewById(R.id.submitfullname1rm1);
        rname[1] = (TextView) findViewById(R.id.submitfullname2rm1);
        rname[2] = (TextView) findViewById(R.id.submitfullname1rm2);
        rname[3] = (TextView) findViewById(R.id.submitfullname2rm2);
        rname[4] = (TextView) findViewById(R.id.submitfullname1rm3);
        rname[5] = (TextView) findViewById(R.id.submitfullname2rm3);
        rollno[0] = (TextView) findViewById(R.id.submitid1rm1);
        rollno[1] = (TextView) findViewById(R.id.submitid2rm1);
        rollno[2] = (TextView) findViewById(R.id.submitid1rm2);
        rollno[3] = (TextView) findViewById(R.id.submitid2rm2);
        rollno[4] = (TextView) findViewById(R.id.submitid1rm3);
        rollno[5] = (TextView) findViewById(R.id.submitid2rm3);


        if(number.equals("1")){
            submitf2.setVisibility(View.GONE);
            submitf3.setVisibility(View.GONE);
        }
        else if(number.equals("2")){
            submitf3.setVisibility(View.GONE);
        }

        for(int i=0;i<2*n;i++){//setting the names and roll no from  wingform array to saved from edittext
            rname[i].setText(sname[i]);
            rollno[i].setText(sid[i]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submitted__form, menu);
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
