package org.kickass.gameserver.boot;

import java.util.concurrent.CountDownLatch;

import org.kickass.server.vo.common.ServerType;
import org.kickass.server.websocket.Server;

public class Boot {

    public static void main(String[] args) throws Exception {
        // bootFromArgs(args);
        bootDevServers();
    }

    private static void bootDevServers() throws Exception {
        final CountDownLatch latch = new CountDownLatch(3);
        startServer(latch, ServerType.CONNECTOR, 3010);
        startServer(latch, ServerType.GATE, 3014);
        startServer(latch, ServerType.AUTH, 3650);
        latch.await();
    }

    private static void startServer(final CountDownLatch latch, final ServerType serverType, final int port) {
        new Thread() {
            public void run() {
                try {
                    new Server(serverType, port).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    latch.countDown();
                }
            }
        };
    }

    private static void bootFromArgs(String[] args) {
        // TODO Auto-generated method stub

    }
}
