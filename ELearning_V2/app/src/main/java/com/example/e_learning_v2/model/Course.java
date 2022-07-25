package com.example.e_learning_v2.model;

import java.util.List;

public class Course {

    private String key;
    private String nama;
    private String logo;
    private int jumlah_bab;
    private List<daftarBab> daftarBabList;

    public Course(){}
    public Course(String nama, String logo, int jumlah_bab, List<daftarBab> daftarBabList) {
        this.nama = nama;
        this.logo = logo;
        this.jumlah_bab = jumlah_bab;
        this.daftarBabList = daftarBabList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getJumlah_bab() {
        return jumlah_bab;
    }

    public void setJumlah_bab(int jumlah_bab) {
        this.jumlah_bab = jumlah_bab;
    }

    public List<daftarBab> getDaftarBabList() {
        return daftarBabList;
    }

    public void setDaftarBabList(List<daftarBab> daftarBabList) {
        this.daftarBabList = daftarBabList;
    }
}
