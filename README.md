# PictureEncryptor
Encrypt picture by using DES3 algorithm.
Encrypt some bytes you prescribed with a string key you give.So it's fast to decrypt.
##Useage
###Encrypt
```java
Byte []encryptedDrawableBytes = Encryptor.encrypt(drawable);
```
###Decrypt
```java
Drawable result = Encryptor.decrypt(encryptedDrawableBytes);
```
###Change the key
```java
Encryptor.setPasswordCryptKey("Your key")
```
###Get the key
```java
String key = Encryptor.getPasswordCryptKey()
```

