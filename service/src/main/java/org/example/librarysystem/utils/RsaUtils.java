package org.example.librarysystem.utils;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtils {
	private static final int DEFAULT_RSA_KEY_SIZE = 2048;

	private static final String KEY_ALGORITHM = "RSA";

	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * RSA私钥解密
	 * @param str  解密字符串
	 * @param privateKey  私钥
	 * @return 明文
	 */
	public static String decryptByPrivateKey(String privateKey,String str) {
		//64位解码加密后的字符串
		byte[] inputByte;
		String outStr = "";
		try {
			inputByte = Base64Utils.decode(str.getBytes("UTF-8"));
			//base64编码的私钥
			byte[] decoded = Base64Utils.decodeFromString(privateKey);
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
			//RSA解密
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			outStr = new String(cipher.doFinal(inputByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outStr;
	}
	public static String encryptByPublicKey(String publicKey, String str) {
		// 64位编码原始字符串
		byte[] inputByte;
		try {
			inputByte = str.getBytes("UTF-8");
			// base64编码的公钥
			byte[] decoded = Base64Utils.decodeFromString(publicKey);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
			// RSA加密
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] encryptedBytes = cipher.doFinal(inputByte);
			// 对加密后的字节数组进行64位编码
			return Base64Utils.encodeToString(encryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(512); // 选择密钥长度
		KeyPair keyPair = keyGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		String encryptByPublicKey = encryptByPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAICsrrSWiDCV0bag/HeB+KwUTBgaKVIz1UYA5EwdR5WFqu57KGaMwZQdoCH2GZtIR4q8Sxb1gK6vxM3l3CowTZUCAwEAAQ==", "123456");
		System.out.println("公钥：" +encryptByPublicKey);
		String decryptByPrivateKey = decryptByPrivateKey("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAgKyutJaIMJXRtqD8d4H4rBRMGBopUjPVRgDkTB1HlYWq7nsoZozBlB2gIfYZm0hHirxLFvWArq/EzeXcKjBNlQIDAQABAkA/l5gcDc0u3cwwCu3bDjPcr4dP3hYP1ygPGb+6UjofjaS08bnyMIrpfoFwRGPnhUz61mVBw5roO8UbO2YJi1NdAiEAyNANuln0DT4tEJpNOMDR1BHP0I6X2iasqSngJZ11BYsCIQCkCW5lIFgzI1HXtdXx80N5iJgglbd1Gfnx1DdnI7edXwIhAJOvH0l7ula68V2SfVRNyoNSjiltlRQk+Tp3x5ciU9u9AiBCXHJlxOUJoyNMAgsQe9pzQ1hP7mGupRihxbm4JxH0PwIgBR/sm0+ihxQRLGEVB3EFrbuvQ3rf/+evqNGJKyuOm6A=",encryptByPublicKey);
		System.out.println("公钥：" +decryptByPrivateKey);
		System.out.println("公钥：" +Base64Utils.encodeToString(publicKey.getEncoded()));
		System.out.println("私钥：" + Base64Utils.encodeToString(privateKey.getEncoded()));
	}
	/*
	 * @Author cms
	 * @Description //TODO $
	 * @Date $ $
	 * @Param $
	 * @return $
	 **/

}
