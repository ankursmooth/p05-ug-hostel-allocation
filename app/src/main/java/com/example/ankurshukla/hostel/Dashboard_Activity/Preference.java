package com.example.ankurshukla.hostel.Dashboard_Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;

import java.util.Arrays;
import java.util.List;

public class Preference extends AppCompatActivity {

    Spinner noOfRoom;
    Spinner hostel1 ,floor1,hostel2,floor2;
    final Context context = this;
    Button next;
    String  hostel[] = new String[]{"",""};
    String  floor[] = new String[]{"",""};
    String number;//number is used for no of rooms in the wing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        next= (Button) findViewById(R.id.nextPref);
        //Inserting values in no of rooms spinner
        noOfRoom= (Spinner) findViewById(R.id.firstspinerNohostel);
        hostel1 = (Spinner)findViewById(R.id.firstspinerhostel);
        floor1 = (Spinner)findViewById(R.id.firstspinerfloor);
        hostel2 = (Spinner)findViewById(R.id.secondspinerhostel);
        floor2 = (Spinner)findViewById(R.id.secondspinerfloor);
        List<String> types_noOfRooms= Arrays.asList(getResources().getStringArray(R.array.NoOfRooms));
        List<String> hostel_types = Arrays.asList(getResources().getStringArray(R.array.hostel_name));
        List<String> floor_types = Arrays.asList(getResources().getStringArray(R.array.floor_types));

        //for no of rooms
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,types_noOfRooms);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noOfRoom.setAdapter(dataAdapter);

        //for first hostel type spinner
        ArrayAdapter<String> hostel_type1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,hostel_types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostel1.setAdapter(hostel_type1);

        //for second hostel type spinner
        ArrayAdapter<String> hostel_type2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,hostel_types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostel2.setAdapter(hostel_type2);

        //for first fllor spinner
        ArrayAdapter<String> floor_1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,floor_types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floor1.setAdapter(floor_1);

        //for second fllor spinner
        ArrayAdapter<String> floor_2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,floor_types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floor2.setAdapter(floor_1);

        //getting the number of rooms from no of room by spinner
        noOfRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                number=parent.getItemAtPosition(position).toString().toLowerCase();
                AppController.setString(Preference.this,"noofrooms",number);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //getting the hostel types from first spinner of hostel1
        hostel1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hostel[0] = parent.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //getting the hostel type form second spinner i.ee hostel2
        hostel2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hostel[1] = parent.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //getting the floor from floor1 spinner in floor string
        floor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                floor[0] = parent.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //getting the floor from floor2 spinner in floor string
        floor2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                floor[1] = parent.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Preference.this,WingForm.class);
                i.putExtra("number",number);//sending no of rooms to wing form class
                i.putExtra("Hostel_type",hostel);//sending hostel types to wing form class
                i.putExtra("Floor_type",floor);//sending floor types to wing form class
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wing_form, menu);
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
