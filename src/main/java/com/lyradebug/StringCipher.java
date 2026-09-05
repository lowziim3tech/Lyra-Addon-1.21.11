package com.lyradebug;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class StringCipher {
    private static final byte[] XOR_KEY = new byte[]{76, 121, 114, 97, 33, 57, 33};

    public static String decode(String string) {
        if (string == null) {
            return "";
        }
        try {
            byte[] byArray = Base64.getDecoder().decode(string);
            byte[] byArray2 = new byte[byArray.length];
            for (int i = 0; i < byArray.length; ++i) {
                byArray2[i] = (byte)(byArray[i] ^ XOR_KEY[i % XOR_KEY.length]);
            }
            return new String(byArray2, StandardCharsets.UTF_8);
        }
        catch (Exception exception) {
            return string;
        }
    }

    public static String decodeAlias(String string) {
        return StringCipher.decode(string);
    }
}

