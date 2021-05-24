package com.company;

public class LoggingLoaderDecorator extends Loader {
    private Loader loader;

    public LoggingLoaderDecorator(Loader loader) {
        super(loader.getStorage(),loader.getProvider());
        this.loader = loader;
    }

    @Override
    public void runProvider() {
        System.out.println("Погрузка товара");
        loader.runProvider();
    }

    @Override
    public boolean isFull() {
        boolean temp = loader.isFull();
        if(temp){
            System.out.println("Склад заполнен");
        }
        return temp;
    }

    @Override
    public void sell() {
        Customer currentCustomer = getStorage().getCurrentCustomer();
        System.out.println("---------------------------");
        System.out.println("Покупатель: " + currentCustomer.getName() + " на площадке");
        System.out.println("Покупатель " + currentCustomer.getName() + " получает свой заказ...");
        loader.sell();
        System.out.println("Покупатель " + currentCustomer.getName() + " получил свой заказ");
        System.out.println("На складе осталось: " + getStorage().getProductsCount());
        System.out.println("---------------------------");
    }

}
