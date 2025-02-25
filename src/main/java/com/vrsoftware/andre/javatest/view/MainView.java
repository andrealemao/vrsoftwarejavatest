package com.vrsoftware.andre.javatest.view;

import com.vrsoftware.andre.javatest.view.dialog.CustomerMainDialog;
import com.vrsoftware.andre.javatest.view.dialog.ProductMainDialog;
import com.vrsoftware.andre.javatest.view.dialog.SalesMainDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel contentPane;
    private JButton customerButton;
    private JButton productButton;
    private JButton salesButton;

    public MainView() {
        setTitle("VR Software Java test");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        // Set the frame location to the center of the screen

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onCustomer(event);
            }
        });

        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onProduct(event);
            }
        });

        salesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onSale(event);
            }
        });
    }

    private void onCustomer(ActionEvent event) {
        CustomerMainDialog customerMainDialog = new CustomerMainDialog();
        customerMainDialog.pack();
        customerMainDialog.setLocationRelativeTo(null);
        customerMainDialog.setVisible(true);
    }

    private void onProduct(ActionEvent event) {
        ProductMainDialog productMainDialog = new ProductMainDialog();
        productMainDialog.pack();
        productMainDialog.setLocationRelativeTo(null);
        productMainDialog.setVisible(true);
    }

    private void onSale(ActionEvent event) {
        SalesMainDialog saleMainDialog = new SalesMainDialog();
        saleMainDialog.pack();
        saleMainDialog.setLocationRelativeTo(null);
        saleMainDialog.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainView");
        frame.setContentPane(new MainView().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
