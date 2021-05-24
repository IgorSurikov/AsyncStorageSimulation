package com.company;

public class Customer {
    private final String name;
    private final int desiredProducts;

    public Customer(int desiredProducts) {
        this(String.valueOf(desiredProducts), desiredProducts);
    }

    public Customer(String name, int desiredProducts) {
        this.name = name;
        this.desiredProducts = desiredProducts;
    }

    public boolean CheckAndPlace(Storage storage){
        return !storage.checkAndPlaceCustomer(this);
    }

    public String getName() {
        return name;
    }

    public int getDesiredProducts() {
        return desiredProducts;
    }

    @Override
    public String toString() {
        return "Покупатель: " + getName() + ". Требуется продуктов: " + String.valueOf(getDesiredProducts()) + ". ";
    }
}