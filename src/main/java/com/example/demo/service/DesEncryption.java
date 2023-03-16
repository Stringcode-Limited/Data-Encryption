package com.example.demo.service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class DesEncryption {

    public String encrypt(String message, String keyString) {

        byte[] keyBytes = Arrays.copyOf(keyString.getBytes (StandardCharsets.UTF_8), 8);

        SecretKey key = new SecretKeySpec(keyBytes, "DES");

        Cipher cipher;

        try {

            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);

        } catch (NoSuchPaddingException e) {

            throw new RuntimeException(e);
        }
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);

        } catch (InvalidKeyException e) {

            throw new RuntimeException(e);
        }

        byte[] encrypted;
        try {
            encrypted = cipher.doFinal(message.getBytes("UTF-8"));
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return new String(encrypted);
    }
}
