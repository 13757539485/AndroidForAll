package com.android.jatpack.lifedata;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author 俞梨
 * @description:
 * @date :2021/9/25 22:33
 */

public class DU {
    private static String strDefaultKey = "national";
    private Cipher decryptCipher;
    private Cipher encryptCipher;

    public DU() {
        this(strDefaultKey);
    }

    public DU(String var1) {
        this.encryptCipher = null;
        this.decryptCipher = null;

        try {
            Key var3 = this.getKey(var1.getBytes());
            this.encryptCipher = Cipher.getInstance("DES");
            this.encryptCipher.init(1, var3);
            this.decryptCipher = Cipher.getInstance("DES");
            this.decryptCipher.init(2, var3);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public static String byteArr2HexStr(byte[] var0) throws Exception {
        int var3 = var0.length;
        StringBuffer var4 = new StringBuffer(var3 * 2);

        for(int var1 = 0; var1 < var3; ++var1) {
            int var2;
            for(var2 = var0[var1]; var2 < 0; var2 += 256) {
            }

            if (var2 < 16) {
                var4.append('0');
            }

            var4.append(Integer.toString(var2, 16));
        }

        return var4.toString();
    }

    private Key getKey(byte[] var1) throws Exception {
        byte[] var3 = new byte[8];

        for(int var2 = 0; var2 < var1.length && var2 < var3.length; ++var2) {
            var3[var2] = (byte)var1[var2];
        }

        return new SecretKeySpec(var3, "DES");
    }

    public static byte[] hexStr2ByteArr(String var0) throws Exception {
        byte[] var4 = var0.getBytes();
        int var2 = var4.length;
        byte[] var5 = new byte[var2 / 2];

        for(int var1 = 0; var1 < var2; var1 += 2) {
            String var3 = new String(var4, var1, 2);
            var5[var1 / 2] = (byte)((byte)Integer.parseInt(var3, 16));
        }

        return var5;
    }

    public String decrypt(String var1) throws Exception {
        return new String(this.decrypt(hexStr2ByteArr(var1)));
    }

    public byte[] decrypt(byte[] var1) throws Exception {
        return this.decryptCipher.doFinal(var1);
    }

    public String encrypt(String var1) throws Exception {
        return byteArr2HexStr(this.encrypt(var1.getBytes()));
    }

    public byte[] encrypt(byte[] var1) throws Exception {
        return this.encryptCipher.doFinal(var1);
    }
}

