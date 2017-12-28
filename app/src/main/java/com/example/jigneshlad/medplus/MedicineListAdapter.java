package com.example.jigneshlad.medplus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jignesh Lad on 06-11-2017.
 */

public class MedicineListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Medicine> mMedicineList;


    public MedicineListAdapter(Context mContext, List<Medicine> mMedicineList){
        this.mContext=mContext;
        this.mMedicineList=mMedicineList;
    }

    @Override
    public int getCount() {
        return mMedicineList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMedicineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_medicine_list, null);
        TextView tvName = (TextView)v.findViewById(R.id.tv_medName);
        TextView tvMedType = (TextView)v.findViewById(R.id.tv_medType);
        TextView tvDescription = (TextView)v.findViewById(R.id.tv_doseTime);
        TextView tvStartdate= (TextView)v.findViewById(R.id.tv_medStart);
        TextView tvEndDate = (TextView)v.findViewById(R.id.tv_medEnd);
        TextView tvFrequency = (TextView)v.findViewById(R.id.tv_medfreq);
        //Set text for TextView
        tvName.setText("Name: "+mMedicineList.get(position).getMedName());
        tvStartdate.setText("Start Date: "+mMedicineList.get(position).getMedStart());
        tvEndDate.setText("End Date: "+mMedicineList.get(position).getMedEnd());
        tvMedType.setText("Medicine Type: "+mMedicineList.get(position).getMedType());
        tvDescription.setText("Doasge Time: "+mMedicineList.get(position).getDoseTime());
        tvFrequency.setText("Frequency: "+mMedicineList.get(position).getMedFreq());
        //Save product id to tag
        v.setTag(mMedicineList.get(position).getId());

        return v;
    }
}
