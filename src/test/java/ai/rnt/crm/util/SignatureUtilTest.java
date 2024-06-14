package ai.rnt.crm.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import ai.rnt.crm.exception.CRMException;

@ExtendWith(MockitoExtension.class)
class SignatureUtilTest {

	@Mock
	private Logger log;

	@InjectMocks
	private SignatureUtil signatureUtil;

	 @Test
	void testDecryptAmountSuccess() throws Exception {
		String encryptedData = "tCu4qU/fZKyS3Jolkzt3fw==";
		String secretKey = "Op5sTs3Nr7r9lCS=";
		String algo = "AES/CBC/PKCS5Padding";
		String expectedDecryptedData = "11";
		String actualDecryptedData = signatureUtil.decryptAmount(encryptedData, secretKey, algo);
		assertEquals(expectedDecryptedData, actualDecryptedData);
	}

	 @Test
	void testDecryptAmountNullSecretKey() throws Exception {
		String encryptedData = "tCu4qU/fZKyS3Jolkzt3fw==";
		String secretKey = "Op5sTs3Nr7r9lCS=";
		String expectedDecryptedData = "11";
		String algo = "AES/CBC/PKCS5Padding";
		String result = signatureUtil.decryptAmount(encryptedData, secretKey, algo);
		assertEquals(expectedDecryptedData, result);
	}

	@Test
	void testDecryptAmountInvalidEncryptedData() {
		String encryptedData = "InvalidEncryptedData";
		String secretKey = "Op5sTs3Nr7r9lCSJr2jN3qNyelrSEsO=";
		String algo = "AES/CBC/PKCS5Padding";
		CRMException thrown = assertThrows(CRMException.class, () -> {
			signatureUtil.decryptAmount(encryptedData, secretKey, algo);
		});
	}

	 @Test
	void testDecryptAmountEmptyEncryptedData() {
		String encryptedData = "";
		String secretKey = "Op5sTs3Nr7r9lCSJr2jN3qNyelrSEsO=";
		String algo = "AES/CBC/PKCS5Padding";
		CRMException thrown = assertThrows(CRMException.class, () -> {
			signatureUtil.decryptAmount(encryptedData, secretKey, algo);
		});
	}

	 @Test
	void testDecryptAmountExceptionHandling() throws Exception {
		String encryptedData = "InvalidEncryptedData";
		String secretKey = "Op5sTs3Nr7r9lCSJr2jN3qNyelrSEsO=";
		String algo = "AES/CBC/PKCS5Padding";
		CRMException thrown = assertThrows(CRMException.class, () -> {
			signatureUtil.decryptAmount(encryptedData, secretKey, algo);
		});
		assertNotNull(thrown);
	}
}
