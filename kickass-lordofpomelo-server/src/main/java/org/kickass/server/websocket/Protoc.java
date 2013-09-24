package org.kickass.server.websocket;

import io.netty.buffer.ByteBuf;

public class Protoc {

    public static final int PKG_HEAD_BYTES = 4;

    public static String strdecode(byte[] bytes) {
        char[] array = new char[bytes.length];
        int offset = 0;
        int charCode = 0;
        int end = bytes.length;
        int length = 0;
        while (offset < end) {
            if (bytes[offset] < 128) {
                charCode = bytes[offset];
                offset += 1;
            } else if (bytes[offset] < 224) {
                charCode = ((bytes[offset] & 0x3f) << 6) + (bytes[offset + 1] & 0x3f);
                offset += 2;
            } else {
                charCode = ((bytes[offset] & 0x0f) << 12) + ((bytes[offset + 1] & 0x3f) << 6)
                        + (bytes[offset + 2] & 0x3f);
                offset += 3;
            }
            array[length] = (char) charCode;
            length++;
        }
        return new String(array, 0, length);
    };

    public static Package decode(ByteBuf byteBuf) {
        int type = byteBuf.getByte(0);
        int index = 1;
        int length = ((byteBuf.getByte(index++)) << 16 | (byteBuf.getByte(index++)) << 8 | byteBuf.getByte(index++)) >>> 0;
        byte[] body = null;
        if (length > 0) {
            body = new byte[length];
            byteBuf.getBytes(PKG_HEAD_BYTES, body, 0, length);
        }
        return new Package(type, body);
    };
    
//    public static Message decodeMessage(byte[] bytes) {
//        int bytesLen = bytes.length || bytes.byteLength;
//        int offset = 0;
//        int id = 0;
//        int route = null;
//
//        // parse flag
//        byte flag = bytes[offset++];
//        boolean compressRoute = flag & MSG_COMPRESS_ROUTE_MASK;
//        int type = (flag >> 1) & MSG_TYPE_MASK;
//
//        // parse id
//        if(msgHasId(type)) {
//          int m = bytes[offset];
//          int i = 0;
//          do{
//            int m = bytes[offset];
//            id = id + ((m & 0x7f) * Math.pow(2,(7*i)));
//            offset++;
//            i++;
//          }while(m >= 128);
//        }
//
//        // parse route
//        if(msgHasRoute(type)) {
//          if(compressRoute) {
//            route = (bytes[offset++]) << 8 | bytes[offset++];
//          } else {
//            byte routeLen = bytes[offset++];
//            if(routeLen > 0) {
//              route = new byte[routeLen];
//              copyArray(route, 0, bytes, offset, routeLen);
//              route = Protocol.strdecode(route);
//            } else {
//              route = '';
//            }
//            offset += routeLen;
//          }
//        }
//
//        // parse body
//        int bodyLen = bytesLen - offset;
//        byte[] body = new byte[bodyLen];
//
//        copyArray(body, 0, bytes, offset, bodyLen);
//
//        return {'id': id, 'type': type, 'compressRoute': compressRoute,
//                'route': route, 'body': body};
//      };
}

class Package {
    private int type;
    private byte[] body;

    public Package(int type, byte[] body) {
        this.type = type;
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}

class Message {
}
