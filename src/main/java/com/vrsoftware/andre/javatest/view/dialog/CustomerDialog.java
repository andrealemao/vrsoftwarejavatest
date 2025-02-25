package com.vrsoftware.andre.javatest.view.dialog;

import com.vrsoftware.andre.javatest.dao.CustomerDAO;
import com.vrsoftware.andre.javatest.domain.entity.Customer;
import lombok.Setter;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerDialog extends JDialog {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JPanel contentPane;
    private JButton buttonOk;
    private JButton buttonCancel;
    private JTextField textFielName;
    private JTextField textFieldPurchaseLimit;
    private JFormattedTextField textFieldInvoiceCloseDate;

    private CustomerDAO customerDAO = new CustomerDAO();
    private CustomerMainDialog mainDialog;
    private Customer customer = new Customer();

    @Setter
    private String operation;

    public CustomerDialog(CustomerMainDialog parent, boolean modal, String title) throws ParseException {
        super(parent, modal);
        this.mainDialog = parent;

        MaskFormatter formatter = new MaskFormatter("##/##/####");
        DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
        textFieldInvoiceCloseDate.setFormatterFactory(factory);

        initComponents("Salvar");
        setTitle(title);
        buttonOk.setText("Salvar");
    }

    public CustomerDialog(CustomerMainDialog parent, boolean modal, String title, Customer cus)
            throws ParseException {
        super(parent, modal);
        this.mainDialog = parent;

        MaskFormatter formatter = new MaskFormatter("##/##/####");
        DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
        textFieldInvoiceCloseDate.setFormatterFactory(factory);

        setOperation(title.equals("Alterar cliente") ? "Alterar" : "Excluir");
        customer = cus;
        initComponents(operation);
        setTitle(title);
        setDisplayFields(cus);
        buttonOk.setText(operation);

        if (operation.equals("Excluir")) {
            textFielName.setEnabled(false);
            textFieldPurchaseLimit.setEnabled(false);
            textFieldInvoiceCloseDate.setEnabled(false);
        }

        if (operation.equals("Alterar"))
            buttonOk.setEnabled(false);
    }

    private void initComponents(String buttonOkText) throws ParseException {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        buttonOk.setText(buttonOkText);

        addButtonsListeners();

        if (!buttonOkText.equals("Salvar"))
            addFieldsKeyListeners();
    }

    private void addButtonsListeners() {
        buttonOk.addActionListener(new ActionListener() {
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
        textFielName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                customerIsUpdated();
            }
        });

        textFieldPurchaseLimit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                customerIsUpdated();
            }
        });

        textFieldInvoiceCloseDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                customerIsUpdated();
            }
        });
    }

    private void customerIsUpdated() {
        Customer cus = new Customer(
                customer.getId(),
                textFielName.getText(),
                Double.valueOf(textFieldPurchaseLimit.getText()),
                LocalDate.parse(textFieldInvoiceCloseDate.getText(), DATE_TIME_FORMATTER)
        );

        buttonOk.setEnabled(!cus.toString().equals(customer.toString()));
    }

    private void setDisplayFields(Customer cus) {
        textFielName.setText(cus.getName());
        textFieldPurchaseLimit.setText(cus.getPurchaseLimit().toString());
        textFieldInvoiceCloseDate.setText(cus.getInvoiceCloseDate().format(DATE_TIME_FORMATTER));
    }

    private void buttonOkActionPerformed(ActionEvent event) {
        try {
            switch (getTitle()) {
                case "Novo cliente":
                    addNewCustomer();
                    break;
                case "Alterar cliente":
                    updateCustomer();
                    break;
                case "Excluir cliente":
                    deleteCustomer();
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

    private void addNewCustomer() throws ParseException {
        String[] options = {"Sim", "Não"};

        int response = JOptionPane.showOptionDialog(mainDialog, "Cadastrar cliente?",
                "Novo de cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        if (response == JOptionPane.YES_OPTION) {
            setCustomerFields();
            customerDAO.save(customer);
            JOptionPane.showMessageDialog(rootPane, "Cadastro de cliente realizado com sucesso!");
            this.dispose();
        }
    }

    private void updateCustomer() throws ParseException {
        String[] options = {"Sim", "Não"};

        int response = JOptionPane.showOptionDialog(mainDialog, "Alterar cliente?",
                "Alterar cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        if (response == JOptionPane.YES_OPTION) {
            setCustomerFields();
            customerDAO.save(this.customer);
            JOptionPane.showMessageDialog(rootPane, "Cadastro alterado com sucesso!");
            this.dispose();
        }
    }

    private void deleteCustomer() throws ParseException {
        String[] options = {"Sim", "Não"};

        int response = JOptionPane.showOptionDialog(mainDialog, "Excluir cliente?",
                "Excluir cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        if (response == JOptionPane.YES_OPTION) {
            setCustomerFields();
            customerDAO.delete(customer.getId());
            JOptionPane.showMessageDialog(rootPane, "Cliente excluído com sucesso!");
            this.dispose();
        }
    }

    private void setCustomerFields() throws ParseException {
        customer.setName(textFielName.getText());
        customer.setPurchaseLimit(Double.valueOf(textFieldPurchaseLimit.getText()));
        customer.setInvoiceCloseDate(LocalDate.parse(textFieldInvoiceCloseDate.getText(), DATE_TIME_FORMATTER));
    }

    private void onCancel() {
        this.setVisible(false);
        this.dispose();
    }
}
