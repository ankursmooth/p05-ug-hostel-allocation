package com.example.ankurshukla.hostel.Dashboard_Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ankurshukla.hostel.R;

import java.util.ArrayList;

public class Student_Notify extends AppCompatActivity {

    ListView list_msg;
    ArrayList msg_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__notify);

        list_msg = (ListView)findViewById(R.id.textNotify);
        msg_list = getIntent().getStringArrayListExtra("msg_list");


        ArrayAdapter<String> arrayAdatpter = new ArrayAdapter<String>(Student_Notify.this,android.R.layout.simple_list_item_1,msg_list);
        list_msg.setAdapter(arrayAdatpter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student__notify, menu);
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
