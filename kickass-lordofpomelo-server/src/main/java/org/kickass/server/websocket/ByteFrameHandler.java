package org.kickass.server.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.ServerHandshakeStateEvent;

public class ByteFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    private boolean handShaked = false;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame frame) throws Exception {
        if (handShaked) {
            ByteBuf byteBuf = frame.content();
            Package decode = Protoc.decode(byteBuf);
            ctx.channel().writeAndFlush(new BinaryWebSocketFrame());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            handShaked = true;
        }
    }

}
