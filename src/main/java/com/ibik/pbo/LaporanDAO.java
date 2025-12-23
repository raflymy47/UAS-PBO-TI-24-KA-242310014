package com.ibik.pbo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class LaporanDAO {

    public DefaultTableModel getLaporanStok() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Nama Barang", "Stok"}, 0
        );

        try {
            Statement st = Koneksi.getConnection().createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT id_barang, nama_barang, stok FROM barang"
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getInt("stok")
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }

    public DefaultTableModel getLaporanBarangMasuk() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Barang", "Supplier", "User", "Jumlah", "Tanggal"}, 0
        );

        try {
            Statement st = Koneksi.getConnection().createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT bm.id_masuk, b.nama_barang, s.nama_supplier, u.username, bm.jumlah, bm.tanggal " +
                "FROM barang_masuk bm " +
                "JOIN barang b ON bm.id_barang = b.id_barang " +
                "JOIN supplier s ON bm.id_supplier = s.id_supplier " +
                "JOIN users u ON bm.id_user = u.id_user"
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_masuk"),
                    rs.getString("nama_barang"),
                    rs.getString("nama_supplier"),
                    rs.getString("username"),
                    rs.getInt("jumlah"),
                    rs.getDate("tanggal")
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }

    public DefaultTableModel getLaporanBarangKeluar() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Barang", "User", "Jumlah", "Tanggal"}, 0
        );

        try {
            Statement st = Koneksi.getConnection().createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT bk.id_keluar, b.nama_barang, u.username, bk.jumlah, bk.tanggal " +
                "FROM barang_keluar bk " +
                "JOIN barang b ON bk.id_barang = b.id_barang " +
                "JOIN users u ON bk.id_user = u.id_user"
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_keluar"),
                    rs.getString("nama_barang"),
                    rs.getString("username"),
                    rs.getInt("jumlah"),
                    rs.getDate("tanggal")
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }
}
