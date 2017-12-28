package com.example.jigneshlad.medplus;

/**
 * Created by Jignesh Lad on 20-11-2017.
 */

public class Doctor {
    private int id;
    private String docName;
    private String docSpec;
    private String docEmail;
    private String docMob;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocSpec() {
        return docSpec;
    }

    public void setDocSpec(String docSpec) {
        this.docSpec = docSpec;
    }

    public String getDocEmail() {
        return docEmail;
    }

    public void setDocEmail(String docEmail) {
        this.docEmail = docEmail;
    }

    public String getDocMob() {
        return docMob;
    }

    public void setDocMob(String docMob) {
        this.docMob = docMob;
    }

    public Doctor(int id, String docName, String docSpec, String docEmail, String docMob) {
        this.id = id;
        this.docName = docName;
        this.docSpec = docSpec;
        this.docEmail = docEmail;
        this.docMob = docMob;
    }
}
