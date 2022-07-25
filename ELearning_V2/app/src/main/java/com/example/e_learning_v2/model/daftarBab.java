package com.example.e_learning_v2.model;

public class daftarBab {

    private String bab;
    private String deskripsi;
    private String video;
    private String pdf;
    private String key;

    public daftarBab(){}
    public daftarBab(String bab, String deskripsi) {
        this.bab = bab;
        this.deskripsi = deskripsi;
        this.video = video;
        this.pdf = pdf;
    }

    public String getBab() {
        return bab;
    }

    public void setBab(String bab) {
        this.bab = bab;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
