package org.kickass.server.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import org.apache.log4j.Logger;
import org.kickass.server.vo.common.ServerType;

public class Server {
    public static final Logger logger = Logger.getLogger(Server.class.getName());

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ServerType serverType;

    private int websocketPort;

    public Server(ServerType serverType, int websocketPort) {
        this.serverType = serverType;
        this.websocketPort = websocketPort;
    }

    public void start() throws Exception {
        try {
            InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(final SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpRequestDecoder(), new HttpObjectAggregator(65536),
                                    new HttpResponseEncoder(), new WebSocketServerProtocolHandler("/"),
                                    new ByteFrameHandler());
                        }
                    });

            Channel ch = sb.bind(websocketPort).sync().channel();
            System.out.println("Web socket server started at port " + websocketPort);
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void shutdown() {
        logger.warn("game server is shutting down...");
    }

    public void setWebsocketPort(int websocketPort) {
        this.websocketPort = websocketPort;
    }

    public ServerType getServerType() {
        return serverType;
    }
}
