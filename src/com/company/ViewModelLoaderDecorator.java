package com.company;

import javax.swing.*;

public class ViewModelLoaderDecorator extends Loader {
    private final Loader loader;
    private final JPanel customerPanel;
    private final JProgressBar progressBar;
    private final JFormattedTextField loaderMode;

    public ViewModelLoaderDecorator(Loader loader, JPanel customerPanel, JProgressBar progressBar, JFormattedTextField loaderMode) {
        super(loader.getStorage(), loader.getProvider());
        this.loader = loader;
        this.customerPanel = customerPanel;
        this.progressBar = progressBar;
        this.loaderMode = loaderMode;
    }

    @Override
    public void runProvider() {
        loaderMode.setText("Погрузка товара");
        loader.runProvider();
        loaderMode.setText("Ожидание");
    }

    @Override
    public boolean isFull() {
        boolean temp = loader.isFull();
        if(temp){
            loaderMode.setText("Ожидание");
        }
        return temp;
    }

    @Override
    public void sell() {
        Customer currentCustomer = getStorage().getCurrentCustomer();
        JFormattedTextField name = (JFormattedTextField) customerPanel.getComponent(1);
        JFormattedTextField desiredProducts = (JFormattedTextField) customerPanel.getComponent(3);
        JProgressBar progressBar = (JProgressBar) customerPanel.getComponent(4);
        name.setText(currentCustomer.getName());
        desiredProducts.setText(String.valueOf(currentCustomer.getDesiredProducts()));
        loaderMode.setText("Обслуживание покупателя");
        customerPanel.setVisible(true);
        for (int i = 0; i < 100; i++) {
            progressBar.setValue(progressBar.getValue() + 1);
            ConcurrentUtils.sleepMilliseconds((int) (Math.random() * (50 - 10)) + 10);
        }
        loader.sell();
        progressBar.setValue(0);
        customerPanel.setVisible(false);
        loaderMode.setText("Ожидание");
    }
}
