package com.company;

public class ViewModelCustomerDecorator extends Customer {
    private Customer customer;
    private SyncJList customersList;

    public ViewModelCustomerDecorator(Customer customer, SyncJList customersList) {
        super(customer.getName(), customer.getDesiredProducts());
        this.customer = customer;
        this.customersList = customersList;
    }

    @Override
    public boolean CheckAndPlace(Storage storage) {
        customersList.startCheckSignal(customer.toString());
        boolean isActive = customer.CheckAndPlace(storage);
        if (!isActive) customersList.deleteSignal(customer.toString());
        customersList.stopCheckSignal(customer.toString());
        return isActive;
    }
}
