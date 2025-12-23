package com.ibik.pbo;

import java.sql.*;

public class BarangKeluarDAO {

    public void insert(int idBarang, int idUser, int jumlah) {
        try {
            Connection c = Koneksi.getConnection();

            // cek stok
            PreparedStatement cek = c.prepareStatement(
                "SELECT stok FROM barang WHERE id_barang = ?"
            );
            cek.setInt(1, idBarang);
            ResultSet rs = cek.executeQuery();

            if (rs.next()) {
                if (rs.getInt("stok") < jumlah) {
                    throw new RuntimeException("Stok tidak mencukupi");
                }
            }

            // simpan transaksi KELUAR
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO barang_keluar (id_barang, id_user, jumlah, tanggal) " +
                "VALUES (?,?,?,CURDATE())"
            );
            ps.setInt(1, idBarang);
            ps.setInt(2, idUser);
            ps.setInt(3, jumlah);
            ps.executeUpdate();

            // kurangi stok
            PreparedStatement ps2 = c.prepareStatement(
                "UPDATE barang SET stok = stok - ? WHERE id_barang = ?"
            );
            ps2.setInt(1, jumlah);
            ps2.setInt(2, idBarang);
            ps2.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
