package org.kickass.server.vo.packet.protoc;

import io.netty.buffer.ByteBuf;

public class Package {

    public static final int PKG_HEAD_BYTES = 4;

    public static final byte TYPE_HANDSHAKE = 1;
    public static final byte TYPE_HANDSHAKE_ACK = 2;
    public static final byte TYPE_HEARTBEAT = 3;
    public static final byte TYPE_DATA = 4;
    public static final byte TYPE_KICK = 5;

    private byte type;
    private byte[] body;

    public Package(byte type, byte[] body) {
        this.type = type;
        this.body = body;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public static Package decodePackage(ByteBuf byteBuf) {
        byte type = byteBuf.getByte(0);
        int length = ((byteBuf.getByte(1)) << 16 | (byteBuf.getByte(2)) << 8 | byteBuf.getByte(3)) >>> 0;
        byte[] body = null;
        if (length > 0) {
            body = new byte[length];
            byteBuf.getBytes(PKG_HEAD_BYTES, body, 0, length);
        }
        return new Package(type, body);
    }

    public byte[] encode(byte type, byte[] body) {
        int length = body != null ? body.length : 0;
        byte[] buffer = new byte[PKG_HEAD_BYTES + length];
        int index = 0;
        buffer[index++] = (byte) (type & 0xff);
        buffer[index++] = (byte) ((length >> 16) & 0xff);
        buffer[index++] = (byte) ((length >> 8) & 0xff);
        buffer[index++] = (byte) (length & 0xff);
        if (body != null) {
            System.arraycopy(body, 0, buffer, index, length);
        }
        return buffer;
    };
}
