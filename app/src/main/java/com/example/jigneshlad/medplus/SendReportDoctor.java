package com.example.jigneshlad.medplus;


import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class SendReportDoctor extends AppCompatActivity {
    Spinner docName;
    EditText subject;
    EditText emailBody;
    Button bt_send;
    DBHelper mydb;

    AdapterView.OnItemSelectedListener onItemSelectedListener1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydb=new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        docName=(Spinner)findViewById(R.id.sp_docname);
        subject=(EditText)findViewById(R.id.et_subject);
        emailBody=(EditText)findViewById(R.id.et_emailbody);
        bt_send=(Button)findViewById(R.id.bt_sendemail);

        //String[] doc_Name = new String[]{"Nothing Found"};




        Cursor resDoc=mydb.getAllData();
        StringBuffer buffer=new StringBuffer();
        buffer.append("Active medicines: \n");
        if(resDoc.getCount() == 0) {
            buffer.append("No active medicines \n");
        }
        int j=0;
        while (resDoc.moveToNext()) {
            j++;
            buffer.append(" "+j+". Name :"+ resDoc.getString(1)+" Medicine Start Date :"+ resDoc.getString(2)+" Medicine End Date :"+ resDoc.getString(3)+" Medicine Type:"+ resDoc.getString(4)+"\n");

        }
        emailBody.setText(buffer.toString());
        Cursor res = mydb.getAllDoctorsName();
        List<String> doc_Name=new ArrayList<>();
        final List<String> doc_Email=new ArrayList<>();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(SendReportDoctor.this,"No doctor Added",Toast.LENGTH_LONG).show();
        }else{
            int i=0;

            while (res.moveToNext()) {
                doc_Name.add(res.getString(0));
                doc_Email.add(res.getString(1));

            }
        }
        ArrayAdapter<String> adapter_doct = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, doc_Name);
        docName.setAdapter(adapter_doct);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to= doc_Email.get(docName.getSelectedItemPosition());
                String sub=subject.getText().toString();
                String emailbody= emailBody.getText().toString();

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
                intent.putExtra(Intent.EXTRA_SUBJECT,sub);
                intent.putExtra(Intent.EXTRA_TEXT,emailbody);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select Email Application"));
            }
        });


    }


}
