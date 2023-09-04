package ai.rnt.crm.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ai.rnt.crm.constants.EncryptionAlgoConstants;
import ai.rnt.crm.exception.CRMException;

/**
 * This Util class is used to encrypt the password into the SHA-1 type.
 * @author Sanket Wakankar
 * @since 17-08-23
 * @version 1.0
 *
 */
public final class Sha1Encryptor {
	
	  public static String encryptThisString(String input)
	    {
	        try {
	            MessageDigest md = MessageDigest.getInstance(EncryptionAlgoConstants.SHA1);
	            byte[] messageDigest = md.digest(input.getBytes());
	            BigInteger no = new BigInteger(1, messageDigest);
	            StringBuilder hashtext =new StringBuilder(no.toString(16));
	            while (hashtext.length() < 32) {
	                hashtext = hashtext.append("0" + hashtext);
	            }
	            return hashtext.toString();
	        }
	        catch (NoSuchAlgorithmException e) {
	            throw new CRMException(e);
	        }
	    }

}
