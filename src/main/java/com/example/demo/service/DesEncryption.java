package com.example.demo.service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class DesEncryption {

    private static final String ALGORITHM = "DES";
    public byte[] encrypt(String plaintext, String KEY) {

        try {
            SecretKey key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plaintext.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
