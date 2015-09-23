package com.hufeiya.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by hufeiya on 15-9-23.
 */
public class Encryptor {

    private byte [] cipherDrawableBytes;
    private final int encryptLength = 1024;//encrpyt how many bytes from the picture head
    private int decryptLength;          //the decrypt length is not equal theencrypt length.
    private byte [] supplementOfLost;      //If the decrypt length is longer than encrypt,it cause lost in drawable's byte array.

    public Drawable encrypt(Drawable drawable){
        cipherDrawableBytes = FormatTools.getInstance().Drawable2Bytes(drawable);
        encryptDrawableBytes(cipherDrawableBytes);
        return FormatTools.getInstance().Bytes2Drawable(cipherDrawableBytes);
    }

    public Drawable decrypt(Drawable encryptedDrawable){
        decryptDrawableBytes(cipherDrawableBytes);
        return FormatTools.getInstance().Bytes2Drawable(cipherDrawableBytes);
    }
    private void encryptDrawableBytes(byte [] drawableBytes){
        byte [] tobeEncrypt = new byte[encryptLength];
        System.arraycopy(drawableBytes,0,tobeEncrypt,0,encryptLength);
        byte [] encrypted = DES3Utils.encryptMode(tobeEncrypt);
        decryptLength = encrypted.length;
        if (decryptLength > encryptLength){
            supplementOfLost = new byte[decryptLength - encryptLength];
            for (int i = 0;i < decryptLength - encryptLength;i++){
                supplementOfLost[i] = drawableBytes[i+encryptLength];
            }
        }
        System.arraycopy(encrypted,0,drawableBytes,0,decryptLength);
    }

    private void decryptDrawableBytes(byte [] encryptedDrawableBytes){
        byte [] tobeDecrypt = new byte[decryptLength];
        System.arraycopy(encryptedDrawableBytes,0,tobeDecrypt,0,decryptLength);
        byte[] decrypted = DES3Utils.decryptMode(tobeDecrypt);
        System.arraycopy(decrypted,0,encryptedDrawableBytes,0,decrypted.length);
        if(supplementOfLost != null){
            for (int i = 0;i < supplementOfLost.length;i++){
                encryptedDrawableBytes[i+encryptLength] = supplementOfLost[i];
            }
            //supplementOfLost = null;
        }
    }
}
