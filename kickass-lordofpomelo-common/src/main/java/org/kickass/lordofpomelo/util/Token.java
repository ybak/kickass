package org.kickass.lordofpomelo.util;

import java.util.Arrays;


public class Token {

    public static String create(long uid, long timestamp, String pwd) {
        String message = uid + "|" + timestamp;
        try {
            return new AESCrypter(pwd).encrypt(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public static long[] parse(String token, String pwd) {
        try {
            String plaintext = new AESCrypter(pwd).decrypt(token);
            String[] split = plaintext.split("\\|");
            long[] result = new long[2];
            result[0] = Long.parseLong(split[0]);
            result[1] = Long.parseLong(split[1]);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);
        String token = Token.create(111L, currentTimeMillis, "password");
        long[] parse = Token.parse(token, "password");
        System.out.println(Arrays.toString(parse));
    }
}
