package com.quanroon.ysq.cryptos;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content AES对称加密解密，采用同一个密钥
 * @date 2020/10/19 11:37
 */
public class AESUtils {
    private static final String AES = "AES";
    //算法：AES，工作模式：CBC（加密分组链接模式，有向量模式）ECB（电子密码本模式,无向量模式）CFB（加密反馈模式）OFB（输出反馈模式），填充模式：PKCS5Padding：加密内容不足8位用余位数补足8位、NoPadding: 加密内容不足8位用0补足8位
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    // 模式有CBC(有向量模式)和ECB(无向量模式)，向量模式可以简单理解为偏移量，使用CBC模式需要定义一个IvParameterSpec对象
    //如果使用ECB的话，就需要自己提供加密方式，jdk默认的有CBC的模式。 ECB模式可以使用bouncycastle
    //在加密解密方法前分别这句话Security.addProvider(new BouncyCastleProvider());
    private static final String cbc_iv = "1234567890123456";

    //创建密匙主要使用SecretKeySpec、KeyGenerator和KeyPairGenerator三个类来创建密匙
    /**
    * @Description: 加密
    * @Author: quanroon.yaosq
    * @Date: 2020/10/19 11:40
    * @Param: [str, key] 待加密字符串，密钥
    * @Return: java.lang.String
    */
    public static String encode(String str, String key){
        String result = "";
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC);
            //根据私钥key创建密钥
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
            //指定向量模式，如果使用ECB则无需指定
            IvParameterSpec ivSpec = new IvParameterSpec(cbc_iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE,  keySpec, ivSpec);
            byte[] bytes = cipher.doFinal(str.getBytes("UTF-8"));
            result = Base64.encodeBase64String(bytes);
        }  catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String decode(String data, String key){
        String result = null;
        try{
            byte[] encryp = Base64.decodeBase64(data);
            Cipher cipher = Cipher.getInstance(AES_CBC);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
            IvParameterSpec ivSpec = new IvParameterSpec(cbc_iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            //进行编解码
            byte[] decoded = cipher.doFinal(encryp);
            result = new String(decoded, "UTF-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //=== Test Entry ===
    public static void main(String[] args) throws Exception {
        CipherTester_v1 test = new AESUtils.CipherTester_v1();
        String encrypt = test.encrypt("你好，我是json数据");
        test.decrypt(encrypt);

        System.out.println("=====================");

        String data = "{\"iDsiplayStart\":\"0\",\"IDisplayLength\":\"10\"}";
        String key = "943a06fc9d31ce12";
        String encode = encode(data, key);
        String desencrypt = decode(encode, key);
        System.out.println("encode：" + encode);
        System.out.println("decode：" + desencrypt);
    }


    /**
    * @Description: 额外的说明使用CBC
    * @Author: quanroon.yaosq
    * @Date: 2020/10/19 15:30
    * @Param:
    * @Return:
    */
    public static class CipherTester_v1 {
        private static final String default_key = "yaoshuangqi11222";    //注意：密钥长度;    //私钥   AES固定格式为128/192/256 bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。
        private static final String default_iv = "1234567890123456";    //初始化向量参数，AES 为16bytes. DES 为8bytes.


        //加密方式： AES128(CBC/PKCS5Padding) + Base64, 私钥：lianghuilonglong,要加密的字符串abcdefg
        public String encrypt(String jsonStr) throws Exception {
            String text = jsonStr;       //要加密的字符串

            Key keySpec = new SecretKeySpec(default_key.getBytes(), "AES");    //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            IvParameterSpec ivSpec = new IvParameterSpec(default_iv.getBytes());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");    //实例化加密类，参数为加密方式，要写全
            cipher.init(Cipher.ENCRYPT_MODE,  keySpec, ivSpec); //初始化，此方法可以采用三种方式，按服务器要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec

            //cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            //SecureRandom random = new SecureRandom();
            //cipher.init(Cipher.ENCRYPT_MODE, keySpec, random);

            byte[] b = cipher.doFinal(text.getBytes());        //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE, 7bit等等。此处看服务器需要什么编码方式
            String encryptText = Base64.encodeBase64String(b);     //Base64、HEX等编解码
            System.out.println("encryptText=" + encryptText);
            return encryptText;
        }

        //加密方式： AES128(CBC/PKCS5Padding) + Base64, 私钥：lianghuilonglong
        public String decrypt(String data) throws Exception {
            String keySpec = default_key;
            String textDeCipher = data;   //从服务器返回的加密字符串，需要解密的字符串
            byte[] byteArray = Base64.decodeBase64(textDeCipher);   //先用Base64解码

            IvParameterSpec ivSpec = new IvParameterSpec(default_iv.getBytes());
            Key key = new SecretKeySpec(keySpec.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);     //与加密时不同MODE:Cipher.DECRYPT_MODE
            byte[] ret = cipher.doFinal(byteArray);
            String rawText = new String(ret, "utf-8");
            System.out.println("rawText=" + rawText);
            return rawText;
        }

    }

    //采用ECB工作模式，即无向量模式
    public static class ECBCipher{

        static final String ALGORITHM = "AES/ECB/PKCS7Padding";

        public static String encode1(String str, String key){
            Security.addProvider(new BouncyCastleProvider());//增加第三方密码包（BC）
            byte[] result = null;
            try{
                Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES"); //生成加密解密需要的Key
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                result = cipher.doFinal(str.getBytes("UTF-8"));
            }catch(Exception e){
                e.printStackTrace();
            }
            return Base64.encodeBase64String(result);
        }

        public static String decode1(String data, String key){

            Security.addProvider(new BouncyCastleProvider());
            String result = null;
            try{
                byte[] encryp = Base64.decodeBase64(data);
                Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES"); //生成加密解密需要的Key
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                byte[] decoded = cipher.doFinal(encryp);
                result = new String(decoded, "UTF-8");
            }catch(Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }
}
