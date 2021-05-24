package com.company;

import javax.swing.*;
import java.awt.*;

class SyncJList extends JList<String> {

    public void deleteSignal(String customerInfo) {
        synchronized (this) {
            DefaultListModel<String> model = (DefaultListModel<String>) this.getModel();
            setSelectionBackground(Color.GREEN);
            int index = model.indexOf(customerInfo);
            setSelectedIndex(index);
            ConcurrentUtils.sleepMilliseconds(2000);
            model.removeElement(customerInfo);
            setSelectionBackground(Color.blue);

        }
    }

    public void startCheckSignal(String customerInfo) {
        synchronized (this) {
            DefaultListModel<String> model = (DefaultListModel<String>) this.getModel();
            int index = model.indexOf(customerInfo);
            addSelectionInterval(index, index);
            ConcurrentUtils.sleepMilliseconds(100);
        }
    }

    public void stopCheckSignal(String customerInfo) {
        synchronized (this) {
            DefaultListModel<String> model = (DefaultListModel<String>) this.getModel();
            int index = model.indexOf(customerInfo);
            removeSelectionInterval(index, index);
        }
    }
}
