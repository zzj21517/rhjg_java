package com.lxh.encode;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class test {
    public static void main(String args[]) throws Exception {
        KeyPair keyPair= RSAUtil.genKeyPair(512);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        System.out.println("publicKeyString=="+publicKeyString);
        System.out.println("privateKeyString=="+privateKeyString);
    }
}
