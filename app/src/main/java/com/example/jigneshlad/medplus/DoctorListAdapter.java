package com.example.jigneshlad.medplus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jignesh Lad on 20-11-2017.
 */

public class DoctorListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Doctor> mDoctorList;

    public DoctorListAdapter(Context mContext, List<Doctor> mDoctorList){
        this.mContext=mContext;
        this.mDoctorList=mDoctorList;
    }

    @Override
    public int getCount() {
        return mDoctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDoctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_doctor_list, null);
        TextView tvdocName = (TextView)v.findViewById(R.id.tv_docName);
        TextView tvdocSpec = (TextView)v.findViewById(R.id.tv_docSpec);
        TextView tvdocEmail = (TextView)v.findViewById(R.id.tv_docEmail);
        TextView tvdocMob = (TextView)v.findViewById(R.id.tv_docMob);
        //Set text for TextView
        tvdocName.setText("Name: "+mDoctorList.get(position).getDocName());
        tvdocSpec.setText("Specialization: "+mDoctorList.get(position).getDocSpec());
        tvdocEmail.setText("Email Id: "+mDoctorList.get(position).getDocEmail());
        tvdocMob.setText("Mobile No: "+mDoctorList.get(position).getDocMob());

        //Save product id to tag
        v.setTag(mDoctorList.get(position).getId());

        return v;
    }
}
