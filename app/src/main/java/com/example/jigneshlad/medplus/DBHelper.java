package com.example.jigneshlad.medplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {
    public static final String  DATABASE_NAME="med.db";
    public static final String  TABLE_NAME="Med_table";
    public static final String  COL_1="ID";
    public static final String  COL_2="MEDNAME";
    public static final String  COL_3="STARTDATE";
    public static final String  COL_4="ENDDATE";
    public static final String  COL_5="MEDTYPE";
    public static final String  COL_6="DOSAGETIME";
    public static final String  COL_7="FREQUENCY";
    public static final String  COL_8="ALARMTONE";
    public static final String  TABLE_NAME2="Doc_table";
    public static final String  DCOL_1="ID";
    public static final String  DCOL_2="DOCNAME";
    public static final String  DCOL_3="DOCSPEC";
    public static final String  DCOL_4="DOCEMAIL";
    public static final String  DCOL_5="DOCMOB";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,MEDNAME TEXT,STARTDATE TEXT,ENDDATE TEXT,MEDTYPE TEXT,DOSAGETIME TEXT,FREQUENCY TEXT,ALARMTONE TEXT)");
        db.execSQL("create table " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DOCNAME TEXT,DOCSPEC TEXT,DOCEMAIL TEXT,DOCMOB TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }

    public boolean insertData(String name, String startDate, String endDate, String medType,String dosageTime, String frequency ,String alarmTune ){
        SQLiteDatabase mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,startDate);
        contentValues.put(COL_4,endDate);
        contentValues.put(COL_5,medType);
        contentValues.put(COL_6,dosageTime);
        contentValues.put(COL_7,frequency);
        contentValues.put(COL_8,alarmTune);
        long res=mydb.insert(TABLE_NAME,null,contentValues);
        System.out.println(res);
        if(res == -1)
            return false;
        else
            return true;
    }

    public boolean insertDoctor(String docname, String docspec, String docemail, String docmob){
        SQLiteDatabase mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DCOL_2,docname);
        contentValues.put(DCOL_3,docspec);
        contentValues.put(DCOL_4,docemail);
        contentValues.put(DCOL_5,docmob);
        long res=mydb.insert(TABLE_NAME2,null,contentValues);
        System.out.println(res);
        if(res == -1)
            return false;
        else
            return true;
    }

    public Integer deleteDoctor (String name) {
        System.out.print(name);
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.print(db.delete(TABLE_NAME2, "DOCNAME = ?",new String[] {name}));
        return 1;
    }

    public Integer deleteSelectedData (String name) {
        System.out.print(name);
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.print(db.delete(TABLE_NAME, "MEDNAME = ?",new String[] {name}));
        return 1;
    }

    public boolean updateDoctor(String docname, String docspec, String docemail, String docmob){
        SQLiteDatabase mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DCOL_2,docname);
        contentValues.put(DCOL_3,docspec);
        contentValues.put(DCOL_4,docemail);
        contentValues.put(DCOL_5,docmob);
        long res=mydb.update(TABLE_NAME2,contentValues,"DOCNAME = ?",new String[] { docname });
        System.out.println(res);
        if(res == -1)
            return false;
        else
            return true;
    }


    public boolean updateData(String name, String startDate, String endDate, String medType,String dosageTime, String frequency, String alarmTune ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,startDate);
        contentValues.put(COL_4,endDate);
        contentValues.put(COL_5,medType);
        contentValues.put(COL_6,dosageTime);
        contentValues.put(COL_7,frequency);
        contentValues.put(COL_8,alarmTune);
        int res=db.update(TABLE_NAME, contentValues, "MEDNAME = ?",new String[] { name });
        if(res == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getAllDoctors() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2,null);
        return res;
    }
    public Cursor getAllDoctorsName() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select DOCNAME,DOCEMAIL from "+TABLE_NAME2,null);
        return res;
    }
    public Cursor getAllMed() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select MEDNAME from "+TABLE_NAME,null);
        return res;
    }
    public Cursor getReqData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.print(name);
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where MEDNAME = '"+name+"'",null);
        return res;
    }

    public Cursor getReqDoctor(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.print(name);
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2+" where DOCNAME = '"+name+"'",null);
        return res;
    }
}
