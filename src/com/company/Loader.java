package com.company;

public class Loader {
    private Storage storage;
    private Provider provider;

    public Loader(Storage storage, Provider provider) {
        this.storage = storage;
        this.provider = provider;
    }

    public final void provide() {
        if (isFull()) {
            return;
        }
        runProvider();
    }

    public void runProvider(){
        provider.start(storage);
        ConcurrentUtils.sleep(3);
        provider.stop();
    }

    public boolean isFull() {
        if (storage.isFull()) {
            return true;
        }
        return false;
    }

    public void sell() {
        var customer = storage.getCurrentCustomer();
        storage.changeProductsCount(-(customer.getDesiredProducts()));
        storage.setCurrentCustomer(null);
    }

    public Storage getStorage() {
        return storage;
    }

    public Provider getProvider() {
        return provider;
    }
}
