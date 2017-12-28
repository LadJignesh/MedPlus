package com.example.jigneshlad.medplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper mydb;
    private ListView lvMedicine;
    private MedicineListAdapter adapter;
    private List<Medicine> mMedicineList;
    private SensorManager sm;
    private float acelVal;
    private float acelLast;
    private float shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydb = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code for Accelrometer
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        //Listview to display medicine
        lvMedicine = (ListView) findViewById(R.id.listview_medicine);
        mMedicineList = new ArrayList<>();
        TextView emptyText = (TextView) findViewById(android.R.id.empty);
        lvMedicine.setEmptyView(emptyText);
        //Getting all the data to display from database
        Cursor res = mydb.getAllData();
        while (res.moveToNext()) {
            mMedicineList.add(new Medicine(res.getInt(0), res.getString(1), res.getString(4), res.getString(5), res.getString(2), res.getString(3), res.getString(6)));
        }
        adapter = new MedicineListAdapter(getApplicationContext(), mMedicineList);
        lvMedicine.setAdapter(adapter);

        lvMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Clicked :" + mMedicineList.get(position).getMedName().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, EditMedDetails.class);
                intent.putExtra("MedName", mMedicineList.get(position).getMedName().toString());
                startActivity(intent);
            }
        });

        lvMedicine.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String name = mMedicineList.get(position).getMedName().toString();
                Toast.makeText(getApplicationContext(), "Deleted :" + mMedicineList.get(position).getMedName().toString(), Toast.LENGTH_LONG).show();
                Integer i = mydb.deleteSelectedData(name);

                if (i == 1) {
                    finish();
                    startActivity(getIntent());
                    return true;

                } else {
                    return false;
                }
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddMedicine.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if (shake > 12) {
                //startActivity(new Intent(MainActivity.this, AddMedicine.class));
                MainActivity.this.finish();
                System.exit(0);
                //Toast toast = Toast.makeText(getApplicationContext(), "Do not shake me", Toast.LENGTH_LONG);
                //toast.show();

            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            finish();
            startActivity(getIntent());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            finish();
            startActivity(getIntent());
        } else if (id == R.id.nav_pharmacy) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));

        } else if (id == R.id.nav_doctorDetails) {
            startActivity(new Intent(MainActivity.this, AddDoctors.class));

        } else if (id == R.id.nav_req_med) {
            startActivity(new Intent(MainActivity.this, SendRequestFriend.class));
        } else if (id == R.id.nav_sendreport) {
            startActivity(new Intent(MainActivity.this, SendReportDoctor.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
