package org.kickass.server.server;

import java.util.Arrays;
import java.util.List;

public class Protocol {

    public static byte[] strencode(String str) {
        byte[] byteArray = new byte[str.length() * 3];
        int offset = 0;
        for (int i = 0; i < str.length(); i++) {
            char charCode = str.charAt(i);
            List<Byte> codes;
            if (charCode <= 0x7f) {
                codes = Arrays.asList((byte) charCode);
            } else if (charCode <= 0x7ff) {
                codes = Arrays.asList((byte) (0xc0 | (charCode >> 6)), (byte) (0x80 | (charCode & 0x3f)));
            } else {
                codes = Arrays.asList((byte) (0xe0 | (charCode >> 12)), (byte) (0x80 | ((charCode & 0xfc0) >> 6)),
                        (byte) (0x80 | (charCode & 0x3f)));
            }
            for (byte code : codes) {
                byteArray[offset] = code;
                ++offset;
            }
        }
        return Arrays.copyOfRange(byteArray, 0, offset);
    }

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

}