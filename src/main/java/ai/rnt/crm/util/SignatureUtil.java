package ai.rnt.crm.util;

import static java.lang.System.arraycopy;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static java.util.Objects.isNull;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Mac.getInstance;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import ai.rnt.crm.exception.CRMException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 17/05/2024
 * @version 1.0
 *
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class SignatureUtil {

	private static final String SECRET_KEY = keyGenerator();

	private static final String HMAC_SHA_256 = "HmacSHA256";

	private static final String GCM_ALGORITHM = "AES/GCM/NoPadding";

	private static final int GCM_TAG_LENGTH = 16 * 8;

	private static final int GCM_IV_LENGTH = 12;

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
		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] key = new byte[32];
			secureRandom.nextBytes(key);
			return getEncoder().encodeToString(key);
		} catch (Exception e) {
			log.error("Got exception while generating the secret key...{}", e);
			throw new CRMException(e);
		}
	}

	public String decryptAmount(String encryptedData, String secretKey) throws Exception {
		log.info("inside the decryptAmount method...{} ", encryptedData, secretKey);
		try {
			if (isNull(secretKey))
				return encryptedData;
			byte[] decodedData = getDecoder().decode(encryptedData);
			byte[] iv = new byte[GCM_IV_LENGTH];
			byte[] ciphertext = new byte[decodedData.length - GCM_IV_LENGTH];

			arraycopy(decodedData, 0, iv, 0, GCM_IV_LENGTH);
			arraycopy(decodedData, GCM_IV_LENGTH, ciphertext, 0, ciphertext.length);

			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(UTF_8), "AES");

			Cipher cipher = Cipher.getInstance(GCM_ALGORITHM);
			cipher.init(DECRYPT_MODE, secretKeySpec, gcmParameterSpec);

			byte[] decryptedBytes = cipher.doFinal(ciphertext);
			return new String(decryptedBytes, UTF_8);
		} catch (Exception e) {
			log.error("Got exception while decrypting the budget amount...{}", e);
			throw new CRMException(e);
		}
	}
}
