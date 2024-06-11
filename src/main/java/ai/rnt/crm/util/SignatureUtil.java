package ai.rnt.crm.util;

import static java.util.Base64.getEncoder;
import static javax.crypto.Mac.getInstance;
import static lombok.AccessLevel.PRIVATE;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import ai.rnt.crm.exception.CRMException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 17/05/2024
 * @version 1.0
 *
 */
@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class SignatureUtil {

	private static final String SECRET_KEY = keyGenerator();

	private static final String HMAC_SHA_256 = "HmacSHA256";

	private static final String SAME_SECRET_KEY = "Op5sTs3Nr7r9lCSJr2jN3qNyelrSEsO=";

	private static final String CBC_ALGORITHM = "AES/CBC/PKCS5Padding";

	public static String generateSignature(String data) {
		log.info("inside the generateSignature method..{}", data);
		try {
			Mac mac = getInstance(HMAC_SHA_256);
			Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), HMAC_SHA_256);
			mac.init(secretKey);
			byte[] hmacData = mac.doFinal(data.getBytes());
			return getEncoder().encodeToString(hmacData);
		} catch (Exception e) {
			log.error("Got exception while generating the signature...{}", e);
			throw new CRMException(e);
		}
	}

	public static boolean verifySignature(String data, String signature) {
		return generateSignature(data).equals(signature);
	}

	public static String keyGenerator() {
		log.info("inside the keyGenerator method..");
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[32];
		secureRandom.nextBytes(key);
		return getEncoder().encodeToString(key);
	}

	public static String decryptAmount(String encryptedData) throws Exception {
		byte[] decodedData = Base64.getDecoder().decode(encryptedData);
		byte[] iv = new byte[16];
		byte[] ciphertext = new byte[decodedData.length - 16];

		System.arraycopy(decodedData, 0, iv, 0, iv.length);
		System.arraycopy(decodedData, 16, ciphertext, 0, ciphertext.length);

		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		SecretKeySpec secretKeySpec = new SecretKeySpec(SAME_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

		Cipher cipher = Cipher.getInstance(CBC_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

		byte[] decryptedBytes = cipher.doFinal(ciphertext);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}
}
