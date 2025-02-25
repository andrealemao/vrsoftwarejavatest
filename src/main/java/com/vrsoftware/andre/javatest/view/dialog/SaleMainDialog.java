package com.vrsoftware.andre.javatest.view.dialog;

import com.vrsoftware.andre.javatest.dao.ProductDAO;
import com.vrsoftware.andre.javatest.dao.SaleDAO;
import com.vrsoftware.andre.javatest.domain.entity.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SaleMainDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonNew;
    private JButton buttonUpdate;
    private JButton buttonDelete;
    private JTable allSalesTable;
    private DefaultTableModel tableModel;

    private SaleDAO saleDAO;
    private Sale sale;

    public SaleMainDialog() {
        saleDAO = new SaleDAO();

//        setTitle("Vendas");
//        setContentPane(contentPane);
//        setModal(true);
//        setTable();
//        buttonUpdate.setEnabled(false);
//        buttonDelete.setEnabled(false);
//        addButtonListeners();
//
//        getAllProducts();
    }
}
