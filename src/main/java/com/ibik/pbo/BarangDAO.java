package com.ibik.pbo;

import java.sql.*;
import java.util.*;

public class BarangDAO {

    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();
        try {
            ResultSet rs = Koneksi.getConnection()
                .createStatement()
                .executeQuery("SELECT * FROM barang");

            while (rs.next()) {
                list.add(new Barang(
                    rs.getInt("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getInt("stok")
                ));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void insert(String nama, int stok) {
        try {
            PreparedStatement ps = Koneksi.getConnection().prepareStatement(
                "INSERT INTO barang (nama_barang, stok) VALUES (?, ?)"
            );
            ps.setString(1, nama);
            ps.setInt(2, stok);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(int id, String nama, int stok) {
        try {
            PreparedStatement ps = Koneksi.getConnection().prepareStatement(
                "UPDATE barang SET nama_barang=?, stok=? WHERE id_barang=?"
            );
            ps.setString(1, nama);
            ps.setInt(2, stok);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement ps = Koneksi.getConnection().prepareStatement(
                "DELETE FROM barang WHERE id_barang=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
