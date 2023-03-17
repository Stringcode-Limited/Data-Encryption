package com.example.demo.service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class DesDecryption {

    private static final String ALGORITHM = "DES";
    public  String decrypt(String encryptedStr,String KEY) {
        try {

            String[] parts = encryptedStr.substring(1, encryptedStr.length()-1).split(", ");
            byte[] encrypted = new byte[parts.length];
            for (int i = 0; i < parts.length; i++) {
                encrypted[i] = Byte.parseByte(parts[i]);
            }

            SecretKey key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
