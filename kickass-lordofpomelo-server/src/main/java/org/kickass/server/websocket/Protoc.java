package org.kickass.server.websocket;

import java.util.Arrays;

public class Protoc {

    public static final int PKG_HEAD_BYTES = 4;

    public String strdecode(byte[] bytes) {
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

    public Package decode(byte[] bytes) {
        int type = bytes[0];
        int index = 1;
        int length = ((bytes[index++]) << 16 | (bytes[index++]) << 8 | bytes[index++]) >>> 0;
        byte[] body = null;
        if (length > 0) {
            body = Arrays.copyOfRange(bytes, PKG_HEAD_BYTES, length);
        }
        return new Package(type, body);
    };
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
