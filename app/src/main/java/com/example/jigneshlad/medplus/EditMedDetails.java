package com.example.jigneshlad.medplus;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditMedDetails extends AppCompatActivity {

    DBHelper mydb;
    Calendar myCalendar;
    EditText et_MedName;
    EditText et_StartDate;
    EditText et_EndDate;
    Spinner sp_MedType;
    EditText et_DoseTime;
    Spinner sp_Frequency;
    Spinner sp_AlarmTune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_med_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myCalendar = Calendar.getInstance();
        mydb = new DBHelper(this);
        et_MedName = (EditText) findViewById(R.id.et_MedName_edit);
        et_StartDate = (EditText) findViewById(R.id.et_StartDate_edit);
        et_EndDate = (EditText) findViewById(R.id.et_EndDate_edit);
        sp_MedType = (Spinner) findViewById(R.id.sp_MedicineType_edit);
        et_DoseTime = (EditText) findViewById(R.id.et_DoseTime_edit);
        sp_Frequency = (Spinner) findViewById(R.id.sp_frequency_edit);
        sp_AlarmTune = (Spinner) findViewById(R.id.sp_alarm_edit);
        String data = getIntent().getExtras().getString("MedName");
        String[] med_type = new String[]{"Tablet", "Syrup"};
        String[] frequency = new String[]{"One", "Two", "Three"};

        Cursor res = mydb.getReqData(data);
        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }
        while (res.moveToNext()) {
            et_MedName.setText(res.getString(1));
            et_StartDate.setText(res.getString(2));
            et_EndDate.setText(res.getString(3));
            sp_MedType.setPrompt(res.getString(4));
            et_DoseTime.setText(res.getString(5));
            sp_Frequency.setPrompt(res.getString(6));
            sp_AlarmTune.setPrompt(res.getString(7));

            String compareValue = res.getString(4);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.medType, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_MedType.setAdapter(adapter1);
            if (!compareValue.equals(null)) {
                int spinnerPosition = adapter1.getPosition(compareValue);
                sp_MedType.setSelection(spinnerPosition);
            }

            String compareValue1 = res.getString(7);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.alarmtune, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_AlarmTune.setAdapter(adapter2);
            if (!compareValue.equals(null)) {
                int spinnerPosition = adapter2.getPosition(compareValue1);
                sp_AlarmTune.setSelection(spinnerPosition);
            }
            String compareValue2 = res.getString(6);
            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.frequency, android.R.layout.simple_spinner_item);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_Frequency.setAdapter(adapter3);
            if (!compareValue.equals(null)) {
                int spinnerPosition = adapter3.getPosition(compareValue2);
                sp_Frequency.setSelection(spinnerPosition);
            }
        }

        et_StartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditMedDetails.this, start_date_edit, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_EndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditMedDetails.this, end_date_edit, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_DoseTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditMedDetails.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        et_DoseTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

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

    DatePickerDialog.OnDateSetListener start_date_edit = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStartDate();
        }

    };

    DatePickerDialog.OnDateSetListener end_date_edit = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEndDate();
        }

    };

    private void updateLabelStartDate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_StartDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEndDate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_EndDate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_med, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_med) {
            if (!et_StartDate.getText().toString().isEmpty() && !et_EndDate.getText().toString().isEmpty() && !sp_MedType.getSelectedItem().toString().isEmpty() && !et_DoseTime.getText().toString().isEmpty() &&
                    !sp_AlarmTune.getSelectedItem().toString().isEmpty()) {
            String res = et_MedName.getText().toString() + "," + et_StartDate.getText().toString() + ","
                    + et_EndDate.getText().toString() + "," + sp_MedType.getSelectedItem().toString() + "," + et_DoseTime.getText().toString() + ","
                    + sp_AlarmTune.getSelectedItem().toString();
            boolean isInserted = mydb.updateData(et_MedName.getText().toString(), et_StartDate.getText().toString(),
                    et_EndDate.getText().toString(), sp_MedType.getSelectedItem().toString(), et_DoseTime.getText().toString(),
                    sp_Frequency.getSelectedItem().toString(), sp_AlarmTune.getSelectedItem().toString());
            if (isInserted == true) {
                //Setting alarm
                Calendar cal=Calendar.getInstance();
                String timeStr = et_DoseTime.getText().toString();
                String[]timeParts = timeStr.split(":");
                if(sp_Frequency.getSelectedItem().toString().equalsIgnoreCase("One")) {
                    Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                    cal.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
                    cal.set(Calendar.SECOND, 0);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                }else if(sp_Frequency.getSelectedItem().toString().equalsIgnoreCase("Two")){
                    Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                    cal.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
                    cal.set(Calendar.SECOND, 0);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
                }else if(sp_Frequency.getSelectedItem().toString().equalsIgnoreCase("Thrice")){
                    Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                    cal.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
                    cal.set(Calendar.SECOND, 0);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 8*3600*1000, pendingIntent);
                }
                Toast.makeText(EditMedDetails.this, "Data Updated", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(EditMedDetails.this, MainActivity.class);
                startActivity(myIntent);
            } else {
                Toast.makeText(EditMedDetails.this, "Data Failed To Update", Toast.LENGTH_LONG).show();
            }
            }else{
                Toast.makeText(EditMedDetails.this,"PLEASE FILL ALL THE FIELDS",Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
