package com.vrsoftware.andre.javatest.view.dialog;

import com.vrsoftware.andre.javatest.dao.CustomerDAO;
import com.vrsoftware.andre.javatest.domain.entity.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class CustomerMainDialog extends JDialog implements MouseListener {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JPanel contentPane;
    private JButton buttonNew;
    private JButton buttonUpdate;
    private JButton buttonDelete;
    private JTable customerTable;
    private DefaultTableModel tableModel;

    private CustomerDAO customerDAO;
    private Customer customer;

    public CustomerMainDialog() {
        customerDAO = new CustomerDAO();

        setTitle("Clientes");
        setContentPane(contentPane);
        setModal(true);
        setTable();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
        addButtonListeners();

        getAllCustomers();
    }

    private void setTable() {
        tableModel = new DefaultTableModel();

        tableModel.addColumn("CÓDIGO");
        tableModel.addColumn("NOME");
        tableModel.addColumn("VALOR LIMITE DE VENDA");
        tableModel.addColumn("DATA FECHAMENTO FATURA");

//        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);

        JTableHeader header = customerTable.getTableHeader();
        header.setFont(new Font("Courier New", Font.BOLD, 14));
        header.setForeground(new Color(102, 102, 102));
        header.setPreferredSize(new Dimension(100, 35));

        customerTable.setModel(tableModel);
        customerTable.setRowHeight(30);
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
        customerTable.setFont(new Font("Courier New", Font.PLAIN, 14));
        customerTable.addMouseListener(this);
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
        String title = "Novo cliente";
        CustomerDialog customerDialog = new CustomerDialog(this, true, title);
        customerDialog.pack();
        customerDialog.setLocationRelativeTo(null);
        customerDialog.setVisible(true);
        getAllCustomers();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void onUpdate() throws ParseException {
        String title = "Alterar cliente";
        CustomerDialog customerDialog = new CustomerDialog(this, true, title, customer);
        customerDialog.pack();
        customerDialog.setLocationRelativeTo(null);
        customerDialog.setVisible(true);
        getAllCustomers();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void onDelete() throws ParseException {
        String title = "Excluir cliente";
        CustomerDialog customerDialog = new CustomerDialog(this, true, title, customer);
        customerDialog.pack();
        customerDialog.setLocationRelativeTo(null);
        customerDialog.setVisible(true);
        getAllCustomers();
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void getAllCustomers() {
        try {
            List<Customer> customerList = this.customerDAO.getAllCustomers();
            tableModel.setRowCount(0);
            customerList.forEach(cus -> {
                tableModel.addRow(new Object[]{
                        cus.getId(),
                        cus.getName(),
                        cus.getPurchaseLimit(),
                        getFormattedInvoiceCloseDate(cus)
                });
            });
//            customerList.sort(Comparator.comparingLong(Customer::getId));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao acessar banco de dados!!");
        }
    }

    private static String getFormattedInvoiceCloseDate(Customer cus) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return cus.getInvoiceCloseDate().format(formatter);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == customerTable) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (e.getClickCount() == 1) {
                    int row = customerTable.getSelectedRow();
                    buttonUpdate.setEnabled(true);
                    buttonDelete.setEnabled(true);

                    if (row != -1) {
                        Long id = Long.parseLong(customerTable.getValueAt(row, 0).toString());
                        String name = customerTable.getValueAt(row, 1).toString();
                        Double purchaseLimit = Double.valueOf(customerTable.getValueAt(row, 2).toString());
                        LocalDate invoiceCloseDate = LocalDate.parse(customerTable.getValueAt(row, 3)
                                .toString(), DATE_TIME_FORMATTER);

                        customer = new Customer(id, name, purchaseLimit, invoiceCloseDate);
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
