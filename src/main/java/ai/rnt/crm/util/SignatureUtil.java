package ai.rnt.crm.util;

import static java.util.Base64.getEncoder;
import static javax.crypto.Mac.getInstance;
import static lombok.AccessLevel.PRIVATE;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Mac;
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

	public static String generateSignature(String data) {
		log.info("inside the generateSignature method..{}", data);
		try {
			Mac mac = getInstance("HmacSHA256");
			Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
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
}
