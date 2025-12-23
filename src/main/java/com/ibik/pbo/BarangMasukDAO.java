package com.ibik.pbo;

import java.sql.*;

public class BarangMasukDAO {

    public void insert(int idBarang, int idSupplier, int idUser, int jumlah) {
        try {
            Connection c = Koneksi.getConnection();

            // simpan transaksi MASUK
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO barang_masuk (id_barang, id_supplier, id_user, jumlah, tanggal) " +
                "VALUES (?,?,?,?,CURDATE())"
            );
            ps.setInt(1, idBarang);
            ps.setInt(2, idSupplier);
            ps.setInt(3, idUser);
            ps.setInt(4, jumlah);
            ps.executeUpdate();

            // tambah stok
            PreparedStatement ps2 = c.prepareStatement(
                "UPDATE barang SET stok = stok + ? WHERE id_barang = ?"
            );
            ps2.setInt(1, jumlah);
            ps2.setInt(2, idBarang);
            ps2.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
