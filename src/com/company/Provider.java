package com.company;

import java.util.concurrent.*;

public class Provider {
    private final int delay;
    private final int supply;
    private ScheduledExecutorService executor;

    public Provider(int delay, int supply) {
        this.delay = delay;
        this.supply = supply;
    }

    public void start(Storage storage) {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(() -> storage.changeProductsCount(supply), 0, delay, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (executor == null) return;
        ConcurrentUtils.stop(executor);
    }
}
