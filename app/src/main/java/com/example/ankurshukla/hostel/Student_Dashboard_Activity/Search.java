package com.example.ankurshukla.hostel.Student_Dashboard_Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search extends AppCompatActivity {

    Spinner searchtype;
    EditText query;
    Button search;
    String stype;
    CardView c1,c2,c3,c4;
    TextView[] eroom = new TextView[4];
    TextView[] ehostel = new TextView[4];
    TextView[] ename1= new TextView[4];//name taken differently
    TextView[] ename2 = new TextView[4];
    TextView[] esid2 = new TextView[4];
    TextView[] esid1 = new TextView[4];//roll no also taken differently same as of respone so that linking can be made easily
    //second array store second name of the room

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        c1 = (CardView)findViewById(R.id.searchcard1);
        c2 = (CardView)findViewById(R.id.searchcard2);
        c3 = (CardView)findViewById(R.id.searchcard3);
        c4 = (CardView)findViewById(R.id.searchcard4);//all card views
        eroom[0] = (TextView)findViewById(R.id.search_room1);
        eroom[1] = (TextView)findViewById(R.id.search_room2);
        eroom[2] = (TextView)findViewById(R.id.search_room3);
        eroom[3] = (TextView)findViewById(R.id.search_room4);
        ehostel[0] = (TextView)findViewById(R.id.search_hostel1);
        ehostel[1] = (TextView)findViewById(R.id.search_hostel2);
        ehostel[2] = (TextView)findViewById(R.id.search_hostel3);
        ehostel[3] = (TextView)findViewById(R.id.search_hostel4);
        ename1[0] = (TextView)findViewById(R.id.searchroom1_sn1);
        ename2[0] = (TextView)findViewById(R.id.searchroom1_sn2);
        ename1[1] = (TextView)findViewById(R.id.searchroom2_sn1);
        ename2[1] = (TextView)findViewById(R.id.searchroom2_sn2);
        ename1[2] = (TextView)findViewById(R.id.searchroom3_sn1);
        ename2[2] = (TextView)findViewById(R.id.searchroom3_sn2);
        ename1[3] = (TextView)findViewById(R.id.searchroom4_sn1);
        ename2[3] = (TextView)findViewById(R.id.searchroom4_sn2);
        esid1[0] = (TextView)findViewById(R.id.searchroom1_id1);
        esid2[0] = (TextView)findViewById(R.id.searchroom1_id2);
        esid1[1] = (TextView)findViewById(R.id.searchroom2_id1);
        esid2[1] = (TextView)findViewById(R.id.searchroom2_id2);
        esid1[2] = (TextView)findViewById(R.id.searchroom3_id1);
        esid2[2] = (TextView)findViewById(R.id.searchroom3_id2);
        esid1[3] = (TextView)findViewById(R.id.searchroom4_id1);
        esid2[3] = (TextView)findViewById(R.id.searchroom4_id2);
        searchtype = (Spinner)findViewById(R.id.search);
        query = (EditText)findViewById(R.id.searchquery);
        search = (Button)findViewById(R.id.btn_Search);

        c1.setVisibility(View.GONE);
        c2.setVisibility(View.GONE);
        c3.setVisibility(View.GONE);
        c4.setVisibility(View.GONE);
        List<String> types = Arrays.asList(getResources().getStringArray(R.array.Search_Types));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchtype.setAdapter(dataAdapter);

        searchtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stype = parent.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String squery = query.getText().toString().toLowerCase();

                if(!stype.isEmpty() && !squery.isEmpty()){
                        functionsearch(stype,squery);
                }else{
                    Toast.makeText(Search.this,"Enter Both credentials",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void functionsearch(final String search,final String query){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_SEARCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    String success = jObj.getString("success");
                    String noofresults= jObj.getString("noofresults");

                    //if no student found then dialog box appear else access to database
                    if(noofresults.equals("0")){
                        final AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(Search.this);
                        // alertdialogBuilder.setTitle("");

                        alertdialogBuilder
                                .setMessage("No Result Found in database")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog = alertdialogBuilder.create();
                        dialog.show();
                    }else{
                        int n = Integer.parseInt(noofresults);
                        String[] hostelid = new String[4];
                        String[] sid1 = new String[4];
                        String[] name1 = new String[4];
                        String[] sid2 = new String[4];
                        String[] name2 = new String[4];
                        String[] roomid = new String[4];

                        JSONArray res = jObj.getJSONArray("res");
                        for(int i=0;i<n;i++){
                            JSONObject jobj1 = res.getJSONObject(i);
                            hostelid[i] = jobj1.getString("hostelid");
                            roomid[i] = jobj1.getString("roomid");
                            sid1[i] = jobj1.getString("sid1");
                            name1[i] = jobj1.getString("name1");
                            sid2[i] = jobj1.getString("sid2");
                             name2[i] = jobj1.getString("name2");
                        }

                        if(noofresults.equals("1")){
                            c1.setVisibility(View.VISIBLE);
                            for(int i=0;i<1;i++){
                                ehostel[i].setText(hostelid[i]);
                                eroom[i].setText(roomid[i]);
                            }
                            for(int j=0;j<1;j++){
                                ename1[j].setText(name1[j]);
                                ename2[j].setText(name2[j]);
                                esid1[j].setText(sid1[j]);
                                esid2[j].setText(sid2[j]);
                            }
                        }

                        if(noofresults.equals("2")){
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            for(int i=0;i<2;i++){
                                ehostel[i].setText(hostelid[i]);
                                eroom[i].setText(roomid[i]);
                            }
                            for(int j=0;j<2;j++){
                                ename1[j].setText(name1[j]);
                                ename2[j].setText(name2[j]);
                                esid1[j].setText(sid1[j]);
                                esid2[j].setText(sid2[j]);
                            }
                        }

                        if(noofresults.equals("3")){
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.VISIBLE);
                            for(int i=0;i<3;i++){
                                ehostel[i].setText(hostelid[i]);
                                eroom[i].setText(roomid[i]);
                            }
                            for(int j=0;j<3;j++){
                                ename1[j].setText(name1[j]);
                                ename2[j].setText(name2[j]);
                                esid1[j].setText(sid1[j]);
                                esid2[j].setText(sid2[j]);
                            }
                        }

                        if(noofresults.equals("4")){
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.VISIBLE);
                            c4.setVisibility(View.VISIBLE);
                            for(int i=0;i<4;i++){
                                ehostel[i].setText(hostelid[i]);
                                eroom[i].setText(roomid[i]);
                            }
                            for(int j=0;j<4;j++){
                                ename1[j].setText(name1[j]);
                                ename2[j].setText(name2[j]);
                                esid1[j].setText(sid1[j]);
                                esid2[j].setText(sid2[j]);
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("search", search);
                params.put("query", query);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
