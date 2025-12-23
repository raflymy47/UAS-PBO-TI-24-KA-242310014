package com.ibik.pbo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductManagementFrame extends JFrame {

    private JTable table;

    private BarangDAO barangDAO = new BarangDAO();
    private BarangMasukDAO masukDAO = new BarangMasukDAO();
    private BarangKeluarDAO keluarDAO = new BarangKeluarDAO();

    public ProductManagementFrame(User user) {

        setTitle("Product Management - " + user.getUsername());
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        table = new JTable();
        loadTable();

        // ================= STAFF =================
        if (!user.getRole().equalsIgnoreCase("admin")) {
            add(new JScrollPane(table));
            return;
        }

        // ================= ADMIN =================
        JSplitPane split = new JSplitPane();
        split.setDividerLocation(320);

        JPanel left = new JPanel(new java.awt.GridLayout(0, 1));

        JTextField txtId = new JTextField();
        txtId.setEditable(false);
        JTextField txtNama = new JTextField();
        JTextField txtStok = new JTextField();

        JButton btnAdd = new JButton("Add Barang");
        JButton btnUpdate = new JButton("Update Barang");
        JButton btnDelete = new JButton("Delete Barang");
        JButton btnMasuk = new JButton("Barang Masuk");
        JButton btnKeluar = new JButton("Barang Keluar");

        left.add(new JLabel("ID"));
        left.add(txtId);
        left.add(new JLabel("Nama Barang"));
        left.add(txtNama);
        left.add(new JLabel("Stok"));
        left.add(txtStok);
        left.add(btnAdd);
        left.add(btnUpdate);
        left.add(btnDelete);
        left.add(btnMasuk);
        left.add(btnKeluar);

        split.setLeftComponent(left);
        split.setRightComponent(new JScrollPane(table));
        add(split);

        // ================= TABLE CLICK =================
        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                txtId.setText(table.getValueAt(r, 0).toString());
                txtNama.setText(table.getValueAt(r, 1).toString());
                txtStok.setText(table.getValueAt(r, 2).toString());
            }
        });

        // ================= MASTER DATA =================
        btnAdd.addActionListener(e -> {

            if (!validInput(txtNama, txtStok)) return;

            barangDAO.insert(
                txtNama.getText().trim(),
                Integer.parseInt(txtStok.getText())
            );

            loadTable();
            clear(txtId, txtNama, txtStok);
        });

        btnUpdate.addActionListener(e -> {

            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Pilih barang terlebih dahulu!",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (!validInput(txtNama, txtStok)) return;

            barangDAO.update(
                Integer.parseInt(txtId.getText()),
                txtNama.getText().trim(),
                Integer.parseInt(txtStok.getText())
            );

            loadTable();
            clear(txtId, txtNama, txtStok);
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus barang?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    barangDAO.delete(Integer.parseInt(txtId.getText()));
                    loadTable();
                    clear(txtId, txtNama, txtStok);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Barang tidak bisa dihapus karena sudah digunakan dalam transaksi",
                        "Delete Ditolak",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        // ================= BARANG MASUK =================
        btnMasuk.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            int jumlah = Integer.parseInt(
                JOptionPane.showInputDialog("Jumlah Barang Masuk")
            );

            int supplier = Integer.parseInt(
                JOptionPane.showInputDialog("ID Supplier")
            );

            masukDAO.insert(
                Integer.parseInt(txtId.getText()),
                supplier,
                user.getId(),
                jumlah
            );

            loadTable();
        });

        // ================= BARANG KELUAR =================
        btnKeluar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            int jumlah = Integer.parseInt(
                JOptionPane.showInputDialog("Jumlah Barang Keluar")
            );

            try {
                keluarDAO.insert(
                    Integer.parseInt(txtId.getText()),
                    user.getId(),
                    jumlah
                );
                loadTable();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Gagal",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    // ================= VALIDASI INPUT =================
    private boolean validInput(JTextField nama, JTextField stok) {

        if (nama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nama barang harus diisi!",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );
            nama.requestFocus();
            return false;
        }

        if (stok.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Stok barang harus diisi!",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );
            stok.requestFocus();
            return false;
        }

        try {
            int s = Integer.parseInt(stok.getText());
            if (s < 0) {
                JOptionPane.showMessageDialog(this,
                    "Stok tidak boleh negatif!",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE
                );
                stok.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Stok harus berupa angka!",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );
            stok.requestFocus();
            return false;
        }

        return true;
    }

    // ================= HELPER =================
    private void loadTable() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Nama", "Stok"}, 0
        );

        for (Barang b : barangDAO.getAll()) {
            model.addRow(new Object[]{
                b.getId(),
                b.getNama(),
                b.getStok()
            });
        }
        table.setModel(model);
    }

    private void clear(JTextField id, JTextField nama, JTextField stok) {
        id.setText("");
        nama.setText("");
        stok.setText("");
    }
}
