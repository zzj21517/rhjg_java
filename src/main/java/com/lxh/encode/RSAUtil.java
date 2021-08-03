package com.lxh.encode;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSAUtil {

	

	// 生成密钥对
	public static KeyPair genKeyPair(int keyLength) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(keyLength);
		return keyPairGenerator.generateKeyPair();
	}


	//
//	/**
//	 * 公钥加密
//	 * 
//	 * @param content
//	 *            待加密字符串
//	 * @param publicKey
//	 *            公钥
//	 * @return 加密后的16进制字符串
//	 * @throws Exception
//	 */
//	public static String publicEncrypt(String content, String publicKey) throws Exception {
//		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
//		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
//		byte[] doFinal = cipher.doFinal(content.getBytes("utf-8"));
//		return bytesToHexString(doFinal);
//	}

	/**
	 * 公钥分段加密
	 * 
	 * @param content
	 *            待加密字符串
	 * @param publicKey
	 *            公钥
	 * @return 加密后的16进制字符串
	 * @throws Exception
	 */
	public static String publicEncrypt(String content, String publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		
		
		int inputLen = content.getBytes("utf-8").length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 244) {
            byte[] cache;
            if(inputLen - offSet > 244) {
                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, 244);
            } else {
                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return bytesToHexString(encryptedData);


	}
	
	
	
//	/**
//	 * 私钥解密
//	 * 
//	 * @param content
//	 *            公钥加密的16进制字符串
//	 * @param privateKey
//	 *            私钥
//	 * @return
//	 * @throws Exception
//	 */
//	public static String privateDecrypt(String content, String privateKey) throws Exception {
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
//		byte[] doFinal = cipher.doFinal(hexStringToBytes(content));
//		return new String(doFinal, "utf-8");
//	}
	
	
	/**
	 * 私钥分段解密
	 * 
	 * @param content
	 *            公钥加密的16进制字符串
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String privateDecrypt(String content, String privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
		int inputLen = hexStringToBytes(content).length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 256) {
            byte[] cache;
            if(inputLen - offSet > 256) {
                cache = cipher.doFinal(hexStringToBytes(content), offSet, 256);
            } else {
                cache = cipher.doFinal(hexStringToBytes(content), offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "utf-8");

	}
	

//	/**
//	 * 私钥加密
//	 * 
//	 * @param content
//	 *            待加密字符串
//	 * @param privateKey
//	 *            私钥
//	 * @return 加密后的16进制字符串
//	 * @throws Exception
//	 */
//	public static String privateEncrypt(String content, String privateKey) throws Exception {
//		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
//		cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
//		byte[] doFinal = cipher.doFinal(content.getBytes("utf-8"));
//		return bytesToHexString(doFinal);
//	}

	
	
	/**
	 * 私钥分段加密
	 * 
	 * @param content
	 *            待加密字符串
	 * @param privateKey
	 *            私钥
	 * @return 加密后的16进制字符串
	 * @throws Exception
	 */
	public static String privateEncrypt(String content, String privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
		int inputLen = content.getBytes("utf-8").length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 244) {
            byte[] cache;
            if(inputLen - offSet > 244) {
                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, 244);
            } else {
                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
       
		return bytesToHexString(encryptedData);
	}
//	/**
//	 * 公钥解密
//	 * 
//	 * @param content
//	 *            私钥加密的16进制字符串
//	 * @param publicKey
//	 *            公钥
//	 * @return
//	 * @throws Exception
//	 */
//	public static String publicDecrypt(String content, String publicKey) throws Exception {
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.DECRYPT_MODE, getPublicKey(publicKey));
//		byte[] doFinal = cipher.doFinal(hexStringToBytes(content));
//		return new String(doFinal, "utf-8");
//	}
	/**
	 * 公钥分段解密
	 * 
	 * @param content
	 *            私钥加密的16进制字符串
	 * @param publicKey
	 *            公钥
	 * @return
	 * @throws Exception
	 */
	public static String publicDecrypt(String content, String publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, getPublicKey(publicKey));
		int inputLen = content.getBytes("utf-8").length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 256) {
            byte[] cache;
            if(inputLen - offSet > 256) {
                cache = cipher.doFinal(content.getBytes("utf-8"), offSet, 256);
            } else {
                cache = cipher.doFinal(content.getBytes("utf-8"), offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
		return new String(decryptedData, "utf-8");
	}

	/**
	 * 将base64编码后的公钥字符串转成PublicKey实例
	 * 
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(String publicKey) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] keyBytes = decoder.decodeBuffer(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * 将base64编码后的私钥字符串转成PrivateKey实例
	 * 
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	private static PrivateKey getPrivateKey(String privateKey) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] keyBytes = decoder.decodeBuffer(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}


	/**
	 * 将byte数组转换为表示16进制值的字符串
	 * 
	 * @param src
	 * @return
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组
	 * 
	 * @param hexString
	 * @return
	 */
	private static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
