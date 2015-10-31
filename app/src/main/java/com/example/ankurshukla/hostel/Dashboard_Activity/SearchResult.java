package com.example.ankurshukla.hostel.Dashboard_Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ankurshukla.hostel.R;

public class SearchResult extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String [] hostel = intent.getStringArrayExtra("hostelid");
        String [] room = intent.getStringArrayExtra("roomid");
        String [] sid1 = intent.getStringArrayExtra("sid1");
        String [] name1 = intent.getStringArrayExtra("name1");
        String [] sid2 = intent.getStringArrayExtra("sid2");
        String [] name2 = intent.getStringArrayExtra("name2");
        setContentView(R.layout.activity_search_result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
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
