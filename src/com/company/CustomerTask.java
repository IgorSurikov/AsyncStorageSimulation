package com.company;

class CustomerTask implements Runnable {
    private boolean isActive;
    private Customer customer;
    private Storage storage;

    public CustomerTask(Customer customer, Storage storage) {
        this.customer = customer;
        this.storage = storage;
        isActive = true;
    }

    public void run() {
        while (isActive) {
            ConcurrentUtils.sleepMilliseconds(100);
            isActive = customer.CheckAndPlace(storage);
        }
    }
}
