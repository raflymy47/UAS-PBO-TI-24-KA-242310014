package com.ibik.pbo;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login Sistem Gudang");
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        setLayout(new GridLayout(3, 2, 10, 10));
        add(new JLabel("Username"));
        add(txtUser);
        add(new JLabel("Password"));
        add(txtPass);
        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener(e -> {
            User user = new UserDAO().login(
                txtUser.getText(),
                String.valueOf(txtPass.getPassword())
            );

            if (user != null) {
                dispose();
                new ProductManagementFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Login gagal!");
            }
        });
    }
}
