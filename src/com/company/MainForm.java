package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.*;


public class MainForm extends JFrame {
    private JPanel rootPanel;
    private SyncJList customersList;
    private JScrollPane listPanel;
    private JButton startButton;
    private JPanel headerPanel;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel dataPanel;
    private JPanel mainHeaderPanel;
    private JLabel mainHeaderLabel;
    private JTextField productsCountField;
    private JLabel productsCountLabel;
    private JLabel storageIsFullLabel;
    private JLabel loaderModeLabel;
    private JFormattedTextField loaderModeField;
    private JPanel currentCustomerPanel;
    private JLabel customerNameLabel;
    private JLabel desiredProductsLabel;
    private JFormattedTextField customerNameField;
    private JFormattedTextField desiredProductsField;
    private JProgressBar progressBar;
    private JButton addCustomerButton;
    private DefaultListModel<String> customersListModel;
    private ArrayList<Customer> customers;
    private Storage storage;
    private int counter;
    private ExecutorService executor;
    private int customerNumber;


    public MainForm(int productsCount, int maxSize, int customerNumber) {
        super("Симулятор работы склада");
        this.customerNumber = customerNumber;
        storage = new Storage(productsCount, maxSize);
        createListeners();
        setContentPane(rootPanel);
        createUIComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        customers = new ArrayList<Customer>();
        if (productsCount == maxSize) {
            storageIsFullLabel.setVisible(true);
        }
    }

    public void run() {
        for (int i = 0; i < customerNumber; i++) {
            int desiredProducts = (int) (Math.random() * (storage.getMaxSize() / 4 - 1)) + 1;
            Customer c = new Customer(String.valueOf(i + 1), desiredProducts);
            customers.add(c);
            customersListModel.addElement(c.toString());
            counter++;
        }
        Provider provider = new Provider(150, 75);
        executor = Executors.newFixedThreadPool(1000);
        storage.setFormUIControls(productsCountField, storageIsFullLabel);
        Loader loader = new ViewModelLoaderDecorator(new LoggingLoaderDecorator(new Loader(storage, provider)),
                currentCustomerPanel, progressBar, loaderModeField);
        LoaderManager loaderManager = new LoaderManager(loader);
        executor.submit(loaderManager.getLoaderExecutor());
        for (Customer c : customers) {
            executor.submit(new CustomerTask(new ViewModelCustomerDecorator(c,customersList), storage));
            System.out.println("Покупатель: " + c.getName() + " встал в очередь");
        }
        startButton.setEnabled(false);
        addCustomerButton.setEnabled(true);

    }

    private void createListeners() {
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int desiredProducts = (int) (Math.random() * (storage.getMaxSize() / 4 - 1)) + 1;
                Customer c = new Customer(String.valueOf(counter + 1), desiredProducts);
                counter++;
                customersListModel.addElement(c.toString());
                executor.submit(new CustomerTask(new ViewModelCustomerDecorator(c,customersList), storage));
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                run();
            }
        });
    }

    private void createUIComponents() {
        customersListModel = new DefaultListModel<String>();
        customersList.setModel(customersListModel);
        customersList.setSelectionBackground(Color.blue);
        customersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        progressBar.setStringPainted(true);
        progressBar.setString("Получение заказа...");
        progressBar.setForeground(Color.GREEN);
        storageIsFullLabel.setVisible(false);
        productsCountField.setText(String.valueOf(storage.getProductsCount()));
        currentCustomerPanel.setVisible(false);
        addCustomerButton.setEnabled(false);
    }

}
