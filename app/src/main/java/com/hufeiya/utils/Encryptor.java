package com.hufeiya.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by hufeiya on 15-9-23.
 */
public class Encryptor {

    private static final int encryptLength = 1024;//encrpyt how many bytes from the picture head
            //the decrypt length is not equal the encrypt length.


    public static byte [] encrypt(Drawable drawable){
        byte [] cipherDrawableBytes;
        cipherDrawableBytes = FormatTools.getInstance().Drawable2Bytes(drawable);
        cipherDrawableBytes = encryptDrawableBytes(cipherDrawableBytes);
        return cipherDrawableBytes;
    }

    public static Drawable decrypt(byte[] cipherDrawableBytes){
        byte[] result = decryptDrawableBytes(cipherDrawableBytes);
        return FormatTools.getInstance().Bytes2Drawable(result);
    }
    private static byte[] encryptDrawableBytes(byte [] drawableBytes){
        int decryptLength;
        byte [] supplementOfLost;      //If the decrypt length is longer than encrypt,it cause lost in drawable's byte array.
        byte [] tobeEncrypt = new byte[encryptLength];
        System.arraycopy(drawableBytes,0,tobeEncrypt,0,encryptLength);
        byte [] encrypted = DES3Utils.encryptMode(tobeEncrypt);
        decryptLength = encrypted.length;
        int differSize; // If the decrypt length is longer than encrypt, differSize is the differ,or it will be 0.
        if (decryptLength > encryptLength){
            differSize = decryptLength - encryptLength;
        }else {
            differSize = 0;
        }
        supplementOfLost = new byte[differSize+1];
        supplementOfLost[0] = (byte)differSize;
        for (int i = 1;i < differSize + 1;i++) {// drawableBytes[0] = differSize
            supplementOfLost[i] = drawableBytes[i + encryptLength - 1];
        }
        byte[] result = new byte[supplementOfLost.length+drawableBytes.length];
        System.arraycopy(encrypted,0,drawableBytes,0,decryptLength);
        System.arraycopy(supplementOfLost, 0, result, 0, supplementOfLost.length);
        System.arraycopy(drawableBytes, 0, result, supplementOfLost.length, drawableBytes.length);
        return result;
    }

    private static byte[] decryptDrawableBytes(byte [] encryptedDrawableBytes){

        int differSize; // If the decrypt length is longer than encrypt, differSize is the differ,or it will be 0.
        differSize = (int)encryptedDrawableBytes[0];
        int decryptLength = differSize + encryptLength;
        byte [] result;
        result = new byte[encryptedDrawableBytes.length - differSize - 1];
        byte [] tobeDecrypt = new byte[encryptLength + differSize];
        int jumpOverBytes = differSize + 1;
        System.arraycopy(encryptedDrawableBytes,jumpOverBytes,tobeDecrypt,0,decryptLength);
        byte[] decrypted = DES3Utils.decryptMode(tobeDecrypt);
        System.arraycopy(decrypted,0,result,0,decrypted.length);
        System.arraycopy(encryptedDrawableBytes,jumpOverBytes+decrypted.length,result,decrypted.length,result.length-decrypted.length);
        if(differSize != 0){
            System.arraycopy(encryptedDrawableBytes,1,result,decrypted.length,differSize);
        }
        return result;
    }
    public static void setPasswordCryptKey(String key){
        DES3Utils.setPasswordCryptKey(key);
    }
    public static String getPasswordCryptKey(){
        return DES3Utils.getPasswordCryptKey();
    }
}
