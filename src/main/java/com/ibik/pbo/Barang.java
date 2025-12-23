package com.ibik.pbo;

public class Barang {
    private int id;
    private String nama;
    private int stok;

    public Barang(int id, String nama, int stok) {
        this.id = id;
        this.nama = nama;
        this.stok = stok;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public int getStok() { return stok; }
}
