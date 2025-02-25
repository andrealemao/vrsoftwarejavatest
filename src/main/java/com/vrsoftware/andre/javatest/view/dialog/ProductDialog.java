package com.vrsoftware.andre.javatest.view.dialog;

import com.vrsoftware.andre.javatest.dao.ProductDAO;
import com.vrsoftware.andre.javatest.domain.entity.Product;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

public class ProductDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textAreaDescription;
    private JTextField textFieldPrice;
    private JTextField textFieldProductName;

    private ProductDAO productDAO = new ProductDAO();
    private ProductMainDialog mainDialog;
    private Product product = new Product();

    @Setter
    private String operation;

    public ProductDialog(ProductMainDialog parent, boolean modal, String title) {
        super(parent, modal);
        this.mainDialog = parent;

        initComponents("Salvar");
        setTitle(title);
        buttonOK.setText("Salvar");
    }

    public ProductDialog(ProductMainDialog parent, boolean modal, String title, Product prod) {
        super(parent, modal);
        mainDialog = parent;

        setOperation(title.equals("Alterar produto") ? "Alterar" : "Excluir");
        product = prod;
        initComponents(operation);
        setTitle(title);
        setDisplayFields(prod);
        buttonOK.setText(operation);

        if (operation.equals("Excluir")) {
            textAreaDescription.setEnabled(false);
            textFieldPrice.setEnabled(false);
        }

        if (operation.equals("Alterar"))
            buttonOK.setEnabled(false);
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
        textAreaDescription.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                productIsUpdated();
            }
        });

        textFieldPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                productIsUpdated();
            }
        });
    }

    private void productIsUpdated() {
        Product prod = new Product(
                product.getId(),
                product.getName(),
                textAreaDescription.getText(),
                Double.valueOf(textFieldPrice.getText())
        );

        buttonOK.setEnabled(!prod.toString().equals(product.toString()));
    }

    private void setDisplayFields(Product prod) {
        textAreaDescription.setText(prod.getDescription());
        textFieldPrice.setText(prod.getPrice().toString());
    }

    private void buttonOkActionPerformed(ActionEvent event) {
        try {
            switch (getTitle()) {
                case "Novo produto":
                    addNewProduct();
                    break;
                case "Alterar produto":
                    updateProduct();
                    break;
                case "Excluir produto":
                    deleteProduct();
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

    private void addNewProduct() throws ParseException {
        String[] options = {"Sim", "Não"};

        int response = JOptionPane.showOptionDialog(mainDialog, "Cadastrar produto?",
                "Novo de produto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        if (response == JOptionPane.YES_OPTION) {
            setProductFields();
            productDAO.save(product);
            JOptionPane.showMessageDialog(rootPane, "Cadastro de produto realizado com sucesso!");
            this.dispose();
        }
    }

    private void updateProduct() throws ParseException {
        String[] options = {"Sim", "Não"};

        int response = JOptionPane.showOptionDialog(mainDialog, "Alterar produto?",
                "Alterar produto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        if (response == JOptionPane.YES_OPTION) {
            setProductFields();
            productDAO.save(product);
            JOptionPane.showMessageDialog(rootPane, "Cadastro alterado com sucesso!");
            this.dispose();
        }
    }

    private void deleteProduct() throws ParseException {
        String[] options = {"Sim", "Não"};

        int response = JOptionPane.showOptionDialog(mainDialog, "Excluir produto?",
                "Excluir cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        if (response == JOptionPane.YES_OPTION) {
            setProductFields();
            productDAO.delete(product.getId());
            JOptionPane.showMessageDialog(rootPane, "Produto excluído com sucesso!");
            this.dispose();
        }
    }

    private void setProductFields() throws ParseException {
        product.setName(textFieldProductName.getText());
        product.setDescription(textAreaDescription.getText());
        product.setPrice(Double.valueOf(textFieldPrice.getText()));
    }

    private void onCancel() {
        this.setVisible(false);
        this.dispose();
    }
}
