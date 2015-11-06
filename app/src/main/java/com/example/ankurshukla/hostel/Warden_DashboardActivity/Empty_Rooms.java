package com.example.ankurshukla.hostel.Warden_DashboardActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ankurshukla.hostel.R;

public class Empty_Rooms extends AppCompatActivity {

     TextView[] hostel = new TextView[14];
    TextView[] room = new TextView[14];
    TextView[] floor = new TextView[14];
    String[] ghostel,groom,gfloor = new String[25];
    String noofemptyrooms,rqid;
    CardView[] c = new CardView[14];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ghostel = intent.getStringArrayExtra("hostel");
        groom = intent.getStringArrayExtra("room");
        gfloor = intent.getStringArrayExtra("floor");
        noofemptyrooms = intent.getStringExtra("noofemptyrooms");
        rqid = intent.getStringExtra("rqid");
        setContentView(R.layout.activity_empty__rooms);

        c[0] = (CardView)findViewById(R.id.erc1);
        c[1] = (CardView)findViewById(R.id.erc2);
        c[2] = (CardView)findViewById(R.id.erc3);
        c[3] = (CardView)findViewById(R.id.erc4);
        c[4] = (CardView)findViewById(R.id.erc5);
        c[5] = (CardView)findViewById(R.id.erc6);
        c[6] = (CardView)findViewById(R.id.erc7);
        c[7] = (CardView)findViewById(R.id.erc8);
        c[8] = (CardView)findViewById(R.id.erc9);
        c[9] = (CardView)findViewById(R.id.erc10);
        c[10] = (CardView)findViewById(R.id.erc11);
        c[11] = (CardView)findViewById(R.id.erc12);
        c[12] = (CardView)findViewById(R.id.erc13);
        c[13] = (CardView)findViewById(R.id.erc14);
        hostel[0] = (TextView)findViewById(R.id.erhostel1);
        hostel[1] = (TextView)findViewById(R.id.erhostel2);
        hostel[2] = (TextView)findViewById(R.id.erhostel3);
        hostel[3] = (TextView)findViewById(R.id.erhostel4);
        hostel[4] = (TextView)findViewById(R.id.erhostel5);
        hostel[5] = (TextView)findViewById(R.id.erhostel6);
        hostel[6] = (TextView)findViewById(R.id.erhostel7);
        hostel[7] = (TextView)findViewById(R.id.erhostel8);
        hostel[8] = (TextView)findViewById(R.id.erhostel9);
        hostel[9] = (TextView)findViewById(R.id.erhostel10);
        hostel[10] = (TextView)findViewById(R.id.erhostel11);
        hostel[11] = (TextView)findViewById(R.id.erhostel12);
        hostel[12] = (TextView)findViewById(R.id.erhostel13);
        hostel[13] = (TextView)findViewById(R.id.erhostel14);
        room[0] = (TextView)findViewById(R.id.erroom1);
        room[1] = (TextView)findViewById(R.id.erroom2);
        room[2] = (TextView)findViewById(R.id.erroom3);
        room[3] = (TextView)findViewById(R.id.erroom4);
        room[4] = (TextView)findViewById(R.id.erroom5);
        room[5] = (TextView)findViewById(R.id.erroom6);
        room[6] = (TextView)findViewById(R.id.erroom7);
        room[7] = (TextView)findViewById(R.id.erroom8);
        room[8] = (TextView)findViewById(R.id.erroom9);
        room[9] = (TextView)findViewById(R.id.erroom10);
        room[10] = (TextView)findViewById(R.id.erroom11);
        room[11] = (TextView)findViewById(R.id.erroom12);
        room[12] = (TextView)findViewById(R.id.erroom13);
        room[13] = (TextView)findViewById(R.id.erroom14);
        floor[0] = (TextView)findViewById(R.id.erfloor1);
        floor[1] = (TextView)findViewById(R.id.erfloor2);
        floor[2] = (TextView)findViewById(R.id.erfloor3);
        floor[3] = (TextView)findViewById(R.id.erfloor4);
        floor[4] = (TextView)findViewById(R.id.erfloor5);
        floor[5] = (TextView)findViewById(R.id.erfloor6);
        floor[6] = (TextView)findViewById(R.id.erfloor7);
        floor[7] = (TextView)findViewById(R.id.erfloor8);
        floor[8] = (TextView)findViewById(R.id.erfloor9);
        floor[9] = (TextView)findViewById(R.id.erfloor10);
        floor[10] = (TextView)findViewById(R.id.erfloor11);
        floor[11] = (TextView)findViewById(R.id.erfloor12);
        floor[12] = (TextView)findViewById(R.id.erfloor13);
        floor[13] = (TextView)findViewById(R.id.erfloor14);

        for(int i=0;i<14;i++){
            c[i].setVisibility(View.GONE);
        }

        for(int l=0;l<Integer.parseInt(noofemptyrooms);l++){
            c[l].setVisibility(View.VISIBLE);
            hostel[l].setText("Hostel Name- " + ghostel[l]);
            room[l].setText("Room No- " + groom[l]);
            floor[l].setText(gfloor[l] +" Floor");
        }

        c[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this,Room_Allotment.class);
                i.putExtra("hostel",ghostel[0]);
                i.putExtra("room",groom[0]);
                i.putExtra("rqid",rqid);//passing the rqid to the screen where the respond to sr in calledd
                startActivity(i);
            }
        });

        c[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this,Room_Allotment.class);
                i.putExtra("hostel",ghostel[1]);
                i.putExtra("room",groom[1]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[2]);
                i.putExtra("room", groom[2]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[3]);
                i.putExtra("room", groom[3]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[4]);
                i.putExtra("room", groom[4]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[5]);
                i.putExtra("room", groom[5]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });


        c[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[6]);
                i.putExtra("room", groom[6]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });


        c[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[7]);
                i.putExtra("room", groom[7]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[8]);
                i.putExtra("room", groom[8]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });


        c[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[9]);
                i.putExtra("room", groom[9]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this, Room_Allotment.class);
                i.putExtra("hostel", ghostel[10]);
                i.putExtra("room", groom[10]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this,Room_Allotment.class);
                i.putExtra("hostel",ghostel[11]);
                i.putExtra("room",groom[11]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this,Room_Allotment.class);
                i.putExtra("hostel",ghostel[12]);
                i.putExtra("room",groom[12]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });

        c[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Empty_Rooms.this,Room_Allotment.class);
                i.putExtra("hostel",ghostel[13]);
                i.putExtra("room",groom[13]);
                i.putExtra("rqid",rqid);
                startActivity(i);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty__rooms, menu);
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
