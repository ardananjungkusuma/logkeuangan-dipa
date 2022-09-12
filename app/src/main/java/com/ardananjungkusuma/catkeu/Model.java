package com.ardananjungkusuma.catkeu;

public class Model {
    private int id_keuangan;
    private String tanggal, nominal, keterangan, kategori;

    public Model(int id_keuangan, String tanggal, String nominal, String keterangan, String kategori) {
        this.id_keuangan = id_keuangan;
        this.tanggal = tanggal;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.kategori = kategori;
    }

    public int getId_keuangan() {
        return id_keuangan;
    }

    public void setId_keuangan(int id_keuangan) {
        this.id_keuangan = id_keuangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
