package ai.rnt.crm.util;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
/**
 * This Util class is used to get SecretKey of AES algorithm using generateKey method.
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 */
@Component
public class KeyGenerator {
	
	
	public SecretKey generateKey(String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

}
