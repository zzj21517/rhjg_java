package com.lxh.newrhjg.utils;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

public class WechatDecryptDataUtil {
    //解析电话号码
    public static JSONObject getPhoneNumber(String session_key, String encryptedData, String iv) throws IOException {
        System.out.println(0);
        System.out.println(session_key);
        System.out.println(encryptedData);
        System.out.println(iv);
        try {
            // 这个是为了前端传过来可能会有转义字符变成空所以替换
            byte[] dataByte = Base64.decode(encryptedData);
            System.out.println(8);
            // 加密秘钥
            byte[] keyByte = Base64.decode(session_key);
            System.out.println(9);
            // 偏移量
            byte[] ivByte = Base64.decode(iv);
            System.out.println(dataByte.toString());
            System.out.println(keyByte.toString());
            System.out.println(ivByte.toString());
            System.out.println('1');
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                System.out.println('2');
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            System.out.println('3');
            if (null != resultByte && resultByte.length > 0) {
                System.out.println('4');
                String result = new String(resultByte, "UTF-8");
                System.out.println(JSONObject.parseObject(result));
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            System.out.println("异常");
            e.printStackTrace();
        }
        return null;
    }

}