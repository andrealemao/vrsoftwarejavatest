package com.vrsoftware.andre.javatest.view.dialog;

import com.vrsoftware.andre.javatest.dao.SaleDAO;
import com.vrsoftware.andre.javatest.domain.entity.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

;

public class SalesMainDialog extends JDialog implements MouseListener {
    private JPanel contentPane;
    private JButton buttonDelete;
    private JButton buttonUpdate;
    private JButton buttonNew;
    private JTable salesTable;
    private DefaultTableModel tableModel;

    private SaleDAO saleDAO;
    private Sale sale;

    public SalesMainDialog() {
        saleDAO = new SaleDAO();

        setTitle("Vendas");
        setContentPane(contentPane);
        setModal(true);
        setTable();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
        addButtonListeners();

        getAllSales();
    }

    private void setTable() {
        tableModel = new DefaultTableModel();

        tableModel.addColumn("CÓDIGO");
        tableModel.addColumn("CLIENTE");
        tableModel.addColumn("PRODUTO");
        tableModel.addColumn("QTD. DE PRODUTO");

        JTableHeader header = salesTable.getTableHeader();
        header.setFont(new Font("Courier New", Font.BOLD, 14));
        header.setForeground(new Color(102, 102, 102));
        header.setPreferredSize(new Dimension(100, 35));

        salesTable.setModel(tableModel);
        salesTable.setRowHeight(30);
        salesTable.setFont(new Font("Courier New", Font.PLAIN, 14));
        salesTable.addMouseListener(this);
    }

    private void addButtonListeners() {
        buttonNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onNew();
            }
        });

        buttonUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onUpdate();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });
    }

    private void onNew() {
        String title = "Nova venda";
        SalesDialog salesDialog = new SalesDialog(this, true, title);
        salesDialog.pack();
        salesDialog.setLocationRelativeTo(null);
        salesDialog.setVisible(true);
        getAllSales();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void onUpdate() {
        String title = "Alterar venda";
        getAllSales();
    }

    private void onDelete() {
        String title = "Excluir venda";
        getAllSales();
    }

    private void getAllSales() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
