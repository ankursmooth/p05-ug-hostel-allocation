package com.example.ankurshukla.hostel.Dashboard_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search extends AppCompatActivity {

    Spinner searchtype;
    EditText query;
    Button search;
    String stype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchtype = (Spinner)findViewById(R.id.search);
        query = (EditText)findViewById(R.id.searchquery);
        search = (Button)findViewById(R.id.btn_Search);

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
                        String[] hostelid = new String[n];
                        String[] sid1 = new String[n];
                        String[] name1 = new String[n];
                        String[] sid2 = new String[n];
                        String[] name2 = new String[n];
                        String[] roomid = new String[n];

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

                        Intent i = new Intent(Search.this,SearchResult.class);
                        i.putExtra("hostelid",hostelid);
                        i.putExtra("roomid",roomid);
                        i.putExtra("sid1",sid1);
                        i.putExtra("name1",name1);
                        i.putExtra("sid2",sid2);
                        i.putExtra("name",name2);
                        startActivity(i);

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
