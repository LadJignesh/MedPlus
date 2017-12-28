package com.example.jigneshlad.medplus;

/**
 * Created by Jignesh Lad on 06-11-2017.
 */

public class Medicine {
    private int id;
    private String medName;
    private String medType;
    private String doseTime;
    private String medStart;
    private String medEnd;
    private String medFreq;


    public Medicine(int id, String medName, String medType, String doseTime){
        this.id=id;
        this.medName=medName;
        this.medType=medType;
        this.doseTime=doseTime;
    }

    public String getMedStart() {
        return medStart;
    }

    public void setMedStart(String medStart) {
        this.medStart = medStart;
    }

    public String getMedEnd() {
        return medEnd;
    }

    public void setMedEnd(String medEnd) {
        this.medEnd = medEnd;
    }

    public String getMedFreq() {
        return medFreq;
    }

    public void setMedFreq(String medFreq) {
        this.medFreq = medFreq;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedType() {
        return medType;
    }

    public Medicine(int id, String medName, String medType, String doseTime, String medStart, String medEnd, String medFreq) {
        this.id = id;
        this.medName = medName;
        this.medType = medType;
        this.doseTime = doseTime;
        this.medStart = medStart;
        this.medEnd = medEnd;
        this.medFreq = medFreq;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }

    public String getDoseTime() {
        return doseTime;
    }

    public void setDoseTime(String doseTime) {
        this.doseTime = doseTime;
    }
}
