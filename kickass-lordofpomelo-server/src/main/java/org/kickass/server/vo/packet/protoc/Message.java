package org.kickass.server.vo.packet.protoc;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;
import org.kickass.server.server.Protocol;

public class Message {

    public static final byte TYPE_REQUEST = 0;
    public static final byte TYPE_NOTIFY = 1;
    public static final byte TYPE_RESPONSE = 2;
    public static final byte TYPE_PUSH = 3;

    public static final int MSG_FLAG_BYTES = 1;
    public static final int MSG_ROUTE_CODE_BYTES = 2;
    public static final int MSG_ID_MAX_BYTES = 5;
    public static final int MSG_ROUTE_LEN_BYTES = 1;
    public static final char MSG_ROUTE_CODE_MAX = 0xffff;
    public static final byte MSG_COMPRESS_ROUTE_MASK = 0x1;
    public static final byte MSG_TYPE_MASK = 0x7;

    private char id;
    private byte type;
    private boolean compressRoute;
    private String route;
    private byte[] body;

    public Message(char id, byte type, boolean compressRoute, String route, byte[] body) {
        this.id = id;
        this.type = type;
        this.compressRoute = compressRoute;
        this.route = route;
        this.body = body;
    }

    public static byte[] encode(char id, byte type, boolean compressRoute, String route, byte[] msg) {
        // caculate message max length
        int idBytes = msgHasId(type) ? caculateMsgIdBytes(id) : 0;
        int msgLen = MSG_FLAG_BYTES + idBytes;

        if (msgHasRoute(type)) {
            if (compressRoute) {
                if (!NumberUtils.isNumber(route)) {
                    throw new RuntimeException("error flag for number route!");
                }
                msgLen += MSG_ROUTE_CODE_BYTES;
            } else {
                msgLen += MSG_ROUTE_LEN_BYTES;
                if (route != null) {
                    byte[] routeBytes = Protocol.strencode(route);
                    if (routeBytes.length > 255) {
                        throw new RuntimeException("route maxlength is overflow");
                    }
                    msgLen += routeBytes.length;
                }
            }
        }

        if (msg != null) {
            msgLen += msg.length;
        }

        byte[] buffer = new byte[msgLen];
        int offset = 0;
        // add flag
        offset = encodeMsgFlag(type, compressRoute, buffer, offset);
        // add message id
        if (msgHasId(type)) {
            offset = encodeMsgId(id, buffer, offset);
        }
        // add route
        if (msgHasRoute(type)) {
            offset = encodeMsgRoute(compressRoute, route, buffer, offset);
        }
        // add body
        if (msg != null) {
            offset = encodeMsgBody(msg, buffer, offset);
        }
        return buffer;
    };

    public static Message decodeMessage(byte[] bytes) {
        int offset = 0;
        char id = 0;
        String route = null;

        // parse flag
        byte flag = bytes[offset++];
        boolean compressRoute = (flag & MSG_COMPRESS_ROUTE_MASK) == 1;
        byte type = (byte) ((byte) (flag >> 1) & MSG_TYPE_MASK);

        // parse id
        if (Message.msgHasId(type)) {
            byte m;
            int i = 0;
            do {
                m = bytes[offset];
                id = (char) ((id + (char) (m & 0x7f) * (char) Math.pow(2, (7 * i))));
                offset++;
                i++;
            } while (m >= 128);
        }

        // parse route
        if (Message.msgHasRoute(type)) {
            if (compressRoute) {
                route = Integer.toString(((bytes[offset++]) << 8 | bytes[offset++]));
            } else {
                byte routeLen = bytes[offset++];
                if (routeLen > 0) {
                    byte[] routeBytes = Arrays.copyOfRange(bytes, offset, routeLen);
                    route = Protocol.strdecode(routeBytes);
                } else {
                    route = "";
                }
                offset += routeLen;
            }
        }

        // parse body
        int bodyLen = bytes.length - offset;
        byte[] body = Arrays.copyOfRange(bytes, offset, bodyLen);

        return new Message(id, type, compressRoute, route, body);
    }

    private static boolean msgHasRoute(byte type) {
        return type == TYPE_REQUEST || type == TYPE_NOTIFY || type == TYPE_PUSH;
    }

    private static boolean msgHasId(byte type) {
        return type == TYPE_REQUEST || type == TYPE_RESPONSE;
    }

    private static int caculateMsgIdBytes(char id) {
        int len = 0;
        do {
            len += 1;
            id >>= 7;
        } while (id > 0);
        return len;
    };

    private static int encodeMsgFlag(byte type, boolean compressRoute, byte[] buffer, int offset) {
        if (type != TYPE_REQUEST && type != TYPE_NOTIFY && type != TYPE_RESPONSE && type != TYPE_PUSH) {
            throw new RuntimeException("unkonw message type: " + type);
        }
        buffer[offset] = (byte) ((type << 1) | (compressRoute ? 1 : 0));
        return offset + MSG_FLAG_BYTES;
    };

    private static int encodeMsgId(char id, byte[] buffer, int offset) {
        do {
            byte tmp = (byte) (id % 128);
            char next = (char) Math.floor(id / 128);

            if (next != 0) {
                tmp = (byte) (tmp + 128);
            }
            buffer[offset++] = tmp;

            id = next;
        } while (id != 0);

        return offset;
    };

    private static int encodeMsgRoute(boolean compressRoute, String route, byte[] buffer, int offset) {
        if (compressRoute) {
            int routeInt = Integer.parseInt(route);
            if (routeInt > MSG_ROUTE_CODE_MAX) {
                throw new RuntimeException("route number is overflow");
            }

            buffer[offset++] = (byte) ((routeInt >> 8) & 0xff);
            buffer[offset++] = (byte) (routeInt & 0xff);
        } else {
            if (route != null) {
                buffer[offset++] = (byte) (route.length() & 0xff);
                System.arraycopy(route.toCharArray(), 0, buffer, offset, route.length());
                offset += route.length();
            } else {
                buffer[offset++] = 0;
            }
        }
        return offset;
    };

    private static int encodeMsgBody(byte[] msg, byte[] buffer, int offset) {
        System.arraycopy(msg, 0, buffer, offset, msg.length);
        return offset + msg.length;
    };

    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public boolean isCompressRoute() {
        return compressRoute;
    }

    public void setCompressRoute(boolean compressRoute) {
        this.compressRoute = compressRoute;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
