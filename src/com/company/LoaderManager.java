package com.company;

import javax.swing.*;

public class LoaderManager {

    private Loader loader;
    private Storage storage;

    public LoaderManager(Loader loader) {
        this.loader = loader;
        this.storage = loader.getStorage();
    }

    public LoaderManagerExecutor getLoaderExecutor() {
        return new LoaderManagerExecutor(loader);
    }


    class LoaderManagerExecutor implements Runnable {
        private boolean isActive;

        public void disable() {
            isActive = false;
        }

        public LoaderManagerExecutor(Loader loader) {
            isActive = true;
        }

        public void run() {
            while (isActive) {
                ConcurrentUtils.sleep(3);
                if (storage.getCurrentCustomer() == null) {
                    loader.provide();
                } else {
                    loader.sell();
                }
            }
        }
    }


}
