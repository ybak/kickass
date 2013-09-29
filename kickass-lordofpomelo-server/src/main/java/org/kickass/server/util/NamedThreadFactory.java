package org.kickass.server.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private static AtomicInteger counter = new AtomicInteger(1);
    private String name;
    private boolean daemon;
    private int priority;

    public NamedThreadFactory(String name) {
        this(name, true, -1);
    }

    public NamedThreadFactory(String name, boolean daemon) {
        this(name, daemon, -1);
    }

    public NamedThreadFactory(String name, boolean daemon, int priority) {
        this.name = name;
        this.daemon = daemon;
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, name + "[" + counter.getAndIncrement() + "]");
        thread.setDaemon(daemon);
        if (priority != -1) {
            thread.setPriority(priority);
        }
        return thread;
    }
}