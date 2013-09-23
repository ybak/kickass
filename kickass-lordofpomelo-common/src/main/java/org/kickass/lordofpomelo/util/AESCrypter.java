package org.kickass.lordofpomelo.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

public class AESCrypter {

    private static final String ALGO = "AES";
    private byte[] keyValue = new byte[8];
    
    public AESCrypter(String password){
        keyValue = ArrayUtils.subarray(DigestUtils.md5(password), 0, 16); 
    }

    public String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        return Hex.encodeHexString(encVal);
    }

    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Hex.decodeHex(encryptedData.toCharArray());
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }

    public static void main(String[] args) throws Exception {

        String message = "111|34343431122345";
        String password = "PASSWORD";

        String encrypted = new AESCrypter(password).encrypt(message);
        String decrypted = new AESCrypter(password).decrypt(encrypted);

        System.out.println("Encrypt(\"" + message + "\", \"" + password + "\") = \"" + encrypted + "\"");
        System.out.println("Decrypt(\"" + encrypted + "\", \"" + password + "\") = \"" + decrypted + "\"");
    }
}