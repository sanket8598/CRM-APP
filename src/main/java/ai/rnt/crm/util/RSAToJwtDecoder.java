package ai.rnt.crm.util;

import static ai.rnt.crm.constants.EncryptionAlgoConstants.RSA;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import ai.rnt.crm.api.restcontroller.LoginController;

public class RSAToJwtDecoder {

	public static String rsaToJwtDecoder(String inputString) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		PrivateKey privateKey = LoginController.keystore.get(inputString);
		Cipher decryptionCipher = Cipher.getInstance(RSA);
		decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(
				decryptionCipher.doFinal(Base64.getDecoder().decode(inputString)));
		
	}
}
