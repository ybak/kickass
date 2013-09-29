package org.kickass.gameserver.boot;

import org.kickass.server.websocket.Server;

public class Boot {

    public static void main(String[] args) throws Exception {
        new Server(3014).start();;
    }
}
