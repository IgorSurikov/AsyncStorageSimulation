package com.company;

import javax.swing.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Storage {
    private int productsCount;
    private JTextField formProductsCount;
    private JLabel formIsFull;
    private final ReadWriteLock lock;
    private final ReentrantLock reentrantLock;
    private LoaderManager loaderManager;
    private Customer currentCustomer;
    private final int maxSize;

    public Storage(int productsCount, int maxSize) {
        lock = new ReentrantReadWriteLock();
        this.maxSize = maxSize;
        this.productsCount = productsCount;
        this.currentCustomer = null;
        reentrantLock = new ReentrantLock();
    }

    public void changeProductsCount(int delta) {
        lock.writeLock().lock();
        try {
            if (isFull() && delta > 0) {
                formProductsCount.setText(String.valueOf(maxSize));
                formIsFull.setVisible(true);
                return;
            }
            if (productsCount + delta >= maxSize) {
                delta = maxSize - productsCount;
                productsCount = maxSize;
                System.out.println("Количество товара изменилось на: " + delta);
                System.out.println("Склад заполнен");
                formProductsCount.setText(String.valueOf(maxSize));
                formIsFull.setVisible(true);
            } else {
                productsCount += delta;
                System.out.println("Количество товара изменилось на: " + delta);
                formProductsCount.setText(String.valueOf(productsCount));
                formIsFull.setVisible(false);
            }

        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean isFull() {
        return productsCount == maxSize;
    }

    public int getProductsCount() {
        lock.readLock().lock();
        try {
            return productsCount;
        } finally {
            lock.readLock().unlock();
        }
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public void setFormUIControls(JTextField formProductsCount, JLabel isFull) {
        this.formProductsCount = formProductsCount;
        this.formIsFull = isFull;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean checkAndPlaceCustomer(Customer customer) {
        reentrantLock.lock();
        try {
            if (customer.getDesiredProducts() < productsCount && currentCustomer == null) {
                setCurrentCustomer(customer);
                return true;
            } else {
                return false;
            }
        } finally {
            reentrantLock.unlock();
        }
    }
}
