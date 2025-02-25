package com.vrsoftware.andre.javatest.view.dialog;

import com.vrsoftware.andre.javatest.domain.entity.Product;
import com.vrsoftware.andre.javatest.domain.entity.Sale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SalesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxCustomer;
    private JComboBox comboBoxProduct;
    private JTextField textFieldProductQt;
    private JButton buttonUpdateProduct;

    private Sale sale = new Sale();

    public SalesDialog(SalesMainDialog salesMainDialog, boolean b, String title) {
        setContentPane(contentPane);
        setModal(true);

        initComponents("Salvar");
        setTitle(title);
        buttonOK.setText("Salvar");
    }

    private void initComponents(String buttonOkText) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        buttonOK.setText(buttonOkText);

        addButtonsListeners();

        if (!buttonOkText.equals("Salvar"))
            addFieldsKeyListeners();
    }

    private void addButtonsListeners() {
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonOkActionPerformed(event);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void addFieldsKeyListeners() {
        comboBoxProduct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                saleIsUpdated();
            }
        });

        textFieldProductQt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                saleIsUpdated();
            }
        });
    }

    private void saleIsUpdated() {
        Sale sa = new Sale(
                sale.getId(),
                sale.getDate(),
                sale.getCustomer(),
                sale.getItems()
        );

        buttonOK.setEnabled(!sa.toString().equals(sale.toString()));
    }

    private void buttonOkActionPerformed(ActionEvent event) {
        try {
            switch (getTitle()) {
                case "Novo produto":
                    addNewSale();
                    break;
                case "Alterar produto":
                    updateSale();
                    break;
                case "Excluir produto":
                    deleteSale();
                    break;
                default:
                    JOptionPane.showMessageDialog(rootPane, "Ação inválida: " + getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "An error occurred: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNewSale() {
    }

    private void updateSale() {
    }

    private void deleteSale() {
    }

    private void onCancel() {
        this.setVisible(false);
        this.dispose();
    }
}
