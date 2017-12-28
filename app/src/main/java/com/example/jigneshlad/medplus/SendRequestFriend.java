package com.example.jigneshlad.medplus;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SendRequestFriend extends AppCompatActivity {
    EditText friendName;
   // Spinner spM1;
    Spinner spM2;
    Button bt_send;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DBHelper(this);
        setContentView(R.layout.activity_send_report_friend);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        friendName=(EditText)findViewById(R.id.et_FrdName);
       // spM1=(Spinner)findViewById(R.id.sp_med1);
        spM2=(Spinner)findViewById(R.id.sp_med2);

        bt_send=(Button)findViewById(R.id.bt_sendemail);

        friendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
            }
        });
        Cursor res = mydb.getAllMed();
        List<String> medList=new ArrayList<>();
        medList.add("Choose Medicine");
        while (res.moveToNext()) {
            medList.add(res.getString(0));
        }
        ArrayAdapter<String> adapter_med = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, medList);
       // spM1.setAdapter(adapter_med);
        spM2.setAdapter(adapter_med);
       bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call parse()method to send message to number 10000,uri:Universal Resource Identifier
                String mob=friendName.getText().toString();
                String medneeded = spM2.getSelectedItem().toString();;
                String sendbody="Hello, Could you please get me "+medneeded+" medicine.";
                if (medneeded != null) {
                    Uri smsToUri = Uri.parse("smsto:"+mob);
                    // send text +etcontact.getText().toString()+
                    Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                    // set default content of text
                    intent.putExtra("sms_body", sendbody);
                    startActivity(intent);
                } else {
                    Toast.makeText(SendRequestFriend.this, "No medicine selected.", Toast.LENGTH_SHORT).show();
                }
                /*String to= friendName.getText().toString();
                String sub=subject.getText().toString();
                String emailbody= emailBody.getText().toString();

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
                intent.putExtra(Intent.EXTRA_SUBJECT,sub);
                intent.putExtra(Intent.EXTRA_TEXT,emailbody);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select Email Application"));*/
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor =  managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number =       cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //contactName.setText(name);
                friendName.setText(number);
                //contactEmail.setText(email);
            }
        }
    }
}
