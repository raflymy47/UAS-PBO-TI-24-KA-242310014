package com.ibik.pbo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gudang_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root",
                ""  
            );
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
