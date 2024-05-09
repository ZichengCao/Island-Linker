package com.ada.federate.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class HashUtils {

    private static HashMap<String, String> hashMap = new HashMap<>();
    private static MessageDigest md5Digest;

    static {
        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String md5(String input) {
        byte[] messageDigest = md5Digest.digest(input.getBytes());
        String hashVal = bytesToHex(messageDigest);
        hashMap.put(hashVal, input);
        return hashVal;
    }

    public static String decrypt(String hashVal) {
        return hashMap.get(hashVal);
    }

    public static void cleanBuffer() {
        hashMap.clear();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("abc"));
    }
}
