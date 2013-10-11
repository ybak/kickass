package org.kickass.gameserver.boot;

import java.util.concurrent.CountDownLatch;

import org.kickass.server.server.Server;
import org.kickass.server.server.ServerRegistry;
import org.kickass.server.vo.common.ServerType;

public class Boot {
    protected static String zkConnectString = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        // bootFromArgs(args);
        bootDevServers();
    }

    private static void bootDevServers() throws Exception {
        final CountDownLatch latch = new CountDownLatch(3);
//         startServer(latch, ServerType.CONNECTOR, 3010);
         startServer(latch, ServerType.GATE, 3014);
//        startServer(latch, ServerType.AUTH, 3650);
        latch.await();
    }

    private static void startServer(final CountDownLatch latch, final ServerType serverType, final int port) {
        new Thread() {
            public void run() {
                try {
                    Server server = new Server(serverType, port);
                    ServerRegistry serverRegistry = new ServerRegistry(zkConnectString);
                    serverRegistry.init();
                    server.setServerRegistry(serverRegistry);
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    latch.countDown();
                }
            }
        }.start();
    }

    private static void bootFromArgs(String[] args) {
        // TODO Auto-generated method stub

    }
}
