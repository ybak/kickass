package org.kickass.server.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.kickass.server.vo.common.ServerType;

public class ServerRegistry implements Watcher {

    private static final String SERVERS_PREFIX = "/kickass/servers";

    public static final Logger logger = Logger.getLogger(ServerRegistry.class.getName());

    private String zkConnectString;
    private ZooKeeper zk;
    private Map<String, String> servers = new HashMap<String, String>();

    public ServerRegistry(String zkConnectString) {
        this.zkConnectString = zkConnectString;
    }

    public void init() throws IOException, KeeperException, InterruptedException {
        zk = new ZooKeeper(zkConnectString, 5000, this);
        if (zk.exists("/kickass", false) == null) {
            zk.create("/kickass", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create(SERVERS_PREFIX, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        zk.getChildren(SERVERS_PREFIX, this);
    }

    public void regist(ServerType serverType, int port) {
        try {
            String address = InetAddress.getLocalHost().getHostAddress() + ":" + port;
            String path = zk.create("/kickass/servers/" + serverType.toString().toLowerCase(), address.getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            servers.put(path.substring(SERVERS_PREFIX.length() + 1), address);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void process(WatchedEvent event) {
        logger.debug("Event:" + event.getState() + " " + event.getType() + " " + event.getPath());
        if (event.getPath() == null) {
            return;
        }
        try {
            List<String> children = zk.getChildren(SERVERS_PREFIX, this);
            for (String serverName : servers.keySet()) {
                if (!children.contains(serverName)) {
                    servers.remove(serverName);
                    onServerExit(serverName);
                }
            }
            Stat state = new Stat();
            for (String serverPath : children) {
                if (!servers.containsKey(serverPath)) {
                    servers.put(serverPath, new String(zk.getData(SERVERS_PREFIX + "/" + serverPath, false, state)));
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void onServerExit(String serverName) {
        logger.error(serverName + "exited");

    }
}
