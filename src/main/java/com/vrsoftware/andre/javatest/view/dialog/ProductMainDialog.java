package com.vrsoftware.andre.javatest.view.dialog;

import com.vrsoftware.andre.javatest.dao.ProductDAO;
import com.vrsoftware.andre.javatest.domain.entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.List;

public class ProductMainDialog extends JDialog implements MouseListener {
    private JPanel contentPane;
    private JButton buttonNew;
    private JButton buttonUpdate;
    private JButton buttonDelete;
    private JTable productTable;
    private DefaultTableModel tableModel;

    private ProductDAO productDAO;
    private Product product;

    public ProductMainDialog() {
        productDAO = new ProductDAO();

        setTitle("Produtos");
        setContentPane(contentPane);
        setModal(true);
        setTable();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
        addButtonListeners();

        getAllProducts();
    }

    private void setTable() {
        tableModel = new DefaultTableModel();

        tableModel.addColumn("CÓDIGO");
        tableModel.addColumn("DESCRIÇÃO");
        tableModel.addColumn("PREÇO");

//        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);

        JTableHeader header = productTable.getTableHeader();
        header.setFont(new Font("Courier New", Font.BOLD, 14));
        header.setForeground(new Color(102, 102, 102));
        header.setPreferredSize(new Dimension(100, 35));

        productTable.setModel(tableModel);
        productTable.setRowHeight(30);
//        customerTable.setRowSorter(sorter);
//        sorter.setComparator(1, new Comparator<String>() {
//            @Override
//            public int compare(String s1, String s2) {
//                try {
//                    long long1 = Long.parseLong(s1);
//                    long long2 = Long.parseLong(s2);
//                    return Long.compare(long1, long2);
//                } catch (NumberFormatException e) {
//                    // Handle cases where the string is not a valid long
//                    return s1.compareTo(s2); // Fallback to string comparison
//                }
//            }
//        });
        productTable.setFont(new Font("Courier New", Font.PLAIN, 14));
        productTable.addMouseListener(this);
    }

    private void addButtonListeners() {
        buttonNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onNew();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onUpdate();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onDelete();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void onNew() throws ParseException {
        String title = "Novo produto";
        ProductDialog productDialog = new ProductDialog(this, true, title);
        productDialog.pack();
        productDialog.setLocationRelativeTo(null);
        productDialog.setVisible(true);
        getAllProducts();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void onUpdate() throws ParseException {
        String title = "Alterar produto";
        ProductDialog productDialog = new ProductDialog(this, true, title, product);
        productDialog.pack();
        productDialog.setLocationRelativeTo(null);
        productDialog.setVisible(true);
        getAllProducts();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void onDelete() throws ParseException {
        String title = "Excluir produto";
        ProductDialog productDialog = new ProductDialog(this, true, title, product);
        productDialog.pack();
        productDialog.setLocationRelativeTo(null);
        productDialog.setVisible(true);
        getAllProducts();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void getAllProducts() {
        try {
            List<Product> productList = productDAO.getAllProducts();
            tableModel.setRowCount(0);
            productList.forEach(prod -> {
                tableModel.addRow(new Object[]{
                        prod.getId(),
                        prod.getDescription(),
                        prod.getPrice()
                });
            });
//            productList.sort(Comparator.comparingLong(Customer::getId));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao acessar banco de dados!!");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == productTable) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (e.getClickCount() == 1) {
                    int row = productTable.getSelectedRow();
                    buttonUpdate.setEnabled(true);
                    buttonDelete.setEnabled(true);

                    if (row != -1) {
                        Long id = Long.parseLong(productTable.getValueAt(row, 0).toString());
                        String description = productTable.getValueAt(row, 1).toString();
                        Double price = Double.valueOf(productTable.getValueAt(row, 2).toString());

                        product = new Product(id, description, price);
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
