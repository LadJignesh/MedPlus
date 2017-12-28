package com.example.jigneshlad.medplus;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddDoctors extends AppCompatActivity {
    private ListView lvDoctor;
    DBHelper mydb;
    private DoctorListAdapter docadapter;
    private List<Doctor> mDoctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydb = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctors);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddDoctors.this);
                View mView = getLayoutInflater().inflate(R.layout.add_doctor_popup, null);
                final EditText docName = (EditText) mView.findViewById(R.id.et_docName);
                final EditText docSpec = (EditText) mView.findViewById(R.id.et_specialization);
                final EditText docEmail = (EditText) mView.findViewById(R.id.et_docEmail);
                final EditText docMob = (EditText) mView.findViewById(R.id.et_docMob);
                Button btnSave = (Button) mView.findViewById(R.id.bt_addDoc);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!docName.getText().toString().isEmpty() && !docSpec.getText().toString().isEmpty() && !docMob.getText().toString().isEmpty() && !docEmail.getText().toString().isEmpty()) {
                            Toast.makeText(AddDoctors.this, "Doctor Added", Toast.LENGTH_LONG).show();
                            boolean isInserted = mydb.insertDoctor(docName.getText().toString(), docSpec.getText().toString(), docEmail.getText().toString(), docMob.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(AddDoctors.this, "Data Entered", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(getIntent());
                            } else {
                                Toast.makeText(AddDoctors.this, "Data Failed To Enetered", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Toast.makeText(AddDoctors.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        //Listview to display medicine
        lvDoctor = (ListView) findViewById(R.id.listview_doctor);
        mDoctorList = new ArrayList<>();

        //Getting all the data to display from database
        Cursor res = mydb.getAllDoctors();

        //StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            mDoctorList.add(new Doctor(res.getInt(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4)));
        }

        docadapter = new DoctorListAdapter(getApplicationContext(), mDoctorList);
        lvDoctor.setAdapter(docadapter);
        TextView emptyText = (TextView) findViewById(android.R.id.empty);
        lvDoctor.setEmptyView(emptyText);

        lvDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddDoctors.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_edit__doctor_details, null);
                final EditText docName = (EditText) mView.findViewById(R.id.et_docName);
                final EditText docSpec = (EditText) mView.findViewById(R.id.et_specialization);
                final EditText docEmail = (EditText) mView.findViewById(R.id.et_docEmail);
                final EditText docMob = (EditText) mView.findViewById(R.id.et_docMob);
                Button btnUpdate = (Button) mView.findViewById(R.id.bt_updateDoc);

                Cursor res = mydb.getReqDoctor(mDoctorList.get(position).getDocName().toString());
                if (res.getCount() == 0) {
                    // show message
                    showMessage("No Such Doctor Name", "Nothing found");
                    return;
                } else {

                    while (res.moveToNext()) {
                        docName.setText(res.getString(1));
                        docSpec.setText(res.getString(2));
                        docEmail.setText(res.getString(3));
                        docMob.setText(res.getString(4));

                    }
                }
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!docName.getText().toString().isEmpty() && !docSpec.getText().toString().isEmpty() && !docMob.getText().toString().isEmpty() && !docEmail.getText().toString().isEmpty()) {
                            // Toast.makeText(AddDoctors.this, "Doctor Added", Toast.LENGTH_LONG).show();
                            boolean isInserted = mydb.updateDoctor(docName.getText().toString(), docSpec.getText().toString(), docEmail.getText().toString(), docMob.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(AddDoctors.this, "Data Updated", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(getIntent());
                            } else {
                                Toast.makeText(AddDoctors.this, "Data Failed To Update", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Toast.makeText(AddDoctors.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });


        lvDoctor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String name = mDoctorList.get(position).getDocName().toString();
                Toast.makeText(getApplicationContext(), "Deleted :" + mDoctorList.get(position).getDocName().toString(), Toast.LENGTH_LONG).show();
                Integer i = mydb.deleteDoctor(name);

                if (i == 1) {
                    finish();
                    startActivity(getIntent());
                    return true;

                } else {
                    return false;
                }
            }
        });

    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_doc) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddDoctors.this);
            View mView = getLayoutInflater().inflate(R.layout.add_doctor_popup, null);
            final EditText docName = (EditText) mView.findViewById(R.id.et_docName);
            final EditText docSpec = (EditText) mView.findViewById(R.id.et_specialization);
            final EditText docEmail = (EditText) mView.findViewById(R.id.et_docEmail);
            final EditText docMob = (EditText) mView.findViewById(R.id.et_docMob);
            Button btnSave = (Button) mView.findViewById(R.id.bt_addDoc);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!docName.getText().toString().isEmpty() && !docSpec.getText().toString().isEmpty() && !docMob.getText().toString().isEmpty() && !docEmail.getText().toString().isEmpty()) {
                        Toast.makeText(AddDoctors.this, "Doctor Added", Toast.LENGTH_LONG).show();
                        boolean isInserted = mydb.insertDoctor(docName.getText().toString(), docSpec.getText().toString(), docEmail.getText().toString(), docMob.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(AddDoctors.this, "Data Entered", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        } else {
                            Toast.makeText(AddDoctors.this, "Data Failed To Enetered", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(AddDoctors.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    }
                }
            });
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
