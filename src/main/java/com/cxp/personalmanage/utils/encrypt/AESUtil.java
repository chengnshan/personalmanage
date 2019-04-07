package com.cxp.personalmanage.utils.encrypt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {

	private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);
			
	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";// 默认的加密算法

	/**
	 * AES 加密操作
	 *
	 * @param content
	 *            待加密内容
	 * @param key
	 *            加密密钥
	 * @return 返回Base64转码后的加密数据
	 */
	public static String encrypt(String content, String key) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));// 初始化为加密模式的密码器
			byte[] result = cipher.doFinal(byteContent);// 加密

			return Base64.getEncoder().encodeToString(result);// 通过Base64转码返回
		} catch (Exception ex) {
			logger.error( ex.getMessage());
		}

		return null;
	}

	/**
	 * AES 解密操作
	 *
	 * @param content
	 * @param key
	 * @return
	 * 
	 * windows上加解密正常，linux上加密正常，解密时发生如下异常: 
	 * 		javax.crypto.BadPaddingException: Given final block not properly padded
	 * 		经过检查之后，定位在生成KEY的方法上 : https://blog.csdn.net/a277541032/article/details/52016984
	 * 
	 */
	public static String decrypt(String content, String key) {
		logger.info( "AES 解密 入参数 : "+ content);
		try {
			// 实例化
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			// 使用密钥初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
			// 执行操作
			byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));

			return new String(result, "utf-8");
		} catch (Exception ex) {
			logger.error( ex.getMessage());
		}

		return null;
	}

	/**
	 * 生成加密秘钥
	 *
	 * @return
	 */
	private static SecretKeySpec getSecretKey(final String key) {
		// 返回生成指定算法密钥生成器的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
			 secureRandom.setSeed(key.getBytes());
			// AES 要求密钥长度为 128
			kg.init(128, secureRandom);
			// 生成一个密钥
			SecretKey secretKey = kg.generateKey();
			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
		} catch (NoSuchAlgorithmException ex) {
			logger.error( ex.getMessage());
		}

		return null;
	}

	public static void main(String[] args) { 
		String content = "hello,您好";
		String key = "sde@5f98H*^hsff%dfs$r344&df8543*er";
		System.out.println("content:" + content);
		String s1 = AESUtil.encrypt(content, key);
		System.out.println("加密后 : " + s1);
		System.out.println("解密后 : " + AESUtil.decrypt(s1, key));
	}
}
