package com.example.jigneshlad.medplus;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
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

public class AddMedicine extends AppCompatActivity {
    DBHelper mydb;
    Calendar myCalendar;
    EditText et_MedName;
    EditText et_StartDate;
    EditText et_EndDate;
    Spinner sp_MedType;
    EditText et_DoseTime;

    Spinner sp_AlarmTune;
    Spinner sp_Frequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicine);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myCalendar = Calendar.getInstance();
        et_MedName = (EditText) findViewById(R.id.et_MedName);
        et_StartDate = (EditText) findViewById(R.id.et_StartDate);
        et_EndDate = (EditText) findViewById(R.id.et_EndDate);
        sp_MedType = (Spinner) findViewById(R.id.sp_MedicineType);
        et_DoseTime = (EditText) findViewById(R.id.et_DoseTime);
        sp_AlarmTune = (Spinner) findViewById(R.id.sp_alarm);
        sp_Frequency = (Spinner) findViewById(R.id.sp_frequency);

        //Database Function Insert Declaration
        mydb = new DBHelper(this);

        et_StartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddMedicine.this, startdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_EndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddMedicine.this, enddate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
                mTimePicker = new TimePickerDialog(AddMedicine.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        et_DoseTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        //Spinner for Type of Medicine
        Spinner Sp_med_type = (Spinner) findViewById(R.id.sp_MedicineType);
        String[] med_type = new String[]{"Tablet", "Syrup"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, med_type);
        Sp_med_type.setAdapter(adapter);


        //Spinner for Type of Medicine
        Spinner Sp_alarmtone = (Spinner) findViewById(R.id.sp_alarm);
        String[] alarm = new String[]{"Tune 1", "Tune 2", "Tune 3", "Tune 4", "Tune 5", "Tune 6"};
        ArrayAdapter<String> adapter_Alarm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, alarm);
        Sp_alarmtone.setAdapter(adapter_Alarm);

        //Spinner Sp_frequency = (Spinner)findViewById(R.id.sp_frequency);
        String[] frequency = new String[]{"One", "Two", "Thrice"};
        ArrayAdapter<String> adapter_frequency = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, frequency);
        sp_Frequency.setAdapter(adapter_frequency);


    }


    DatePickerDialog.OnDateSetListener startdate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStartDate();
        }

    };

    DatePickerDialog.OnDateSetListener enddate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_medicine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_med) {
            if (!et_MedName.getText().toString().isEmpty() && !et_StartDate.getText().toString().isEmpty() &&
                    !et_EndDate.getText().toString().isEmpty() && !sp_MedType.getSelectedItem().toString().isEmpty() && !et_DoseTime.getText().toString().isEmpty() &&
                    !sp_AlarmTune.getSelectedItem().toString().isEmpty()) {

                boolean isInserted = mydb.insertData(et_MedName.getText().toString(), et_StartDate.getText().toString(), et_EndDate.getText().toString(), sp_MedType.getSelectedItem().toString(), et_DoseTime.getText().toString(),
                        sp_Frequency.getSelectedItem().toString(), sp_AlarmTune.getSelectedItem().toString());

                if (isInserted == true) {
                    Toast.makeText(AddMedicine.this, "Medicine Added", Toast.LENGTH_LONG).show();

                    //Setting alarm
                    Calendar cal = Calendar.getInstance();
                    String timeStr = et_DoseTime.getText().toString();
                    String[] timeParts = timeStr.split(":");

                    if (sp_Frequency.getSelectedItem().toString().equalsIgnoreCase("One")) {
                        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                        cal.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
                        cal.set(Calendar.SECOND, 0);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    } else if (sp_Frequency.getSelectedItem().toString().equalsIgnoreCase("Two")) {
                        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                        cal.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
                        cal.set(Calendar.SECOND, 0);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
                    } else if (sp_Frequency.getSelectedItem().toString().equalsIgnoreCase("Thrice")) {
                        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                        cal.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
                        cal.set(Calendar.SECOND, 0);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 8 * 3600 * 1000, pendingIntent);
                    }

                    Intent myIntent = new Intent(AddMedicine.this, MainActivity.class);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(AddMedicine.this, "Data Failed To Enetered", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(AddMedicine.this, "PLEASE FILL ALL THE FIELDS", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
