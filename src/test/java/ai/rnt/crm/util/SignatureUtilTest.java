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

	//@Test
	void testDecryptAmountSuccess() throws Exception {
		String encryptedData = "m2SJQZtgF3a/wKEd15xoTf4NIxbcZ4b/gFVCaUptVSzp";
		String secretKey = "Op5sTs3Nr7r9lCSJr2jN3qNyelrSEsO=";
		String expectedDecryptedData = "00.00";
		String actualDecryptedData = signatureUtil.decryptAmount(encryptedData);
		assertEquals(expectedDecryptedData, actualDecryptedData);
	}

	//@Test
	void testDecryptAmountNullSecretKey() throws Exception {
		String encryptedData = "DgNqNmEIJF83/gz7oegdphZ4oZ60YqDqw6i1NC+HDDY=";
		String secretKey = null;
		String result = signatureUtil.decryptAmount(encryptedData);
		assertEquals(encryptedData, result);
	}

	//@Test
	void testDecryptAmountInvalidEncryptedData() {
		String encryptedData = "InvalidEncryptedData";
		String secretKey = "Op5sTs3Nr7r9lCSJr2jN3qNyelrSEsO=";
		CRMException thrown = assertThrows(CRMException.class, () -> {
			signatureUtil.decryptAmount(encryptedData);
		});
	}

	//@Test
	void testDecryptAmountEmptyEncryptedData() {
		String encryptedData = "";
		String secretKey = "Op5sTs3Nr7r9lCSJr2jN3qNyelrSEsO=";
		CRMException thrown = assertThrows(CRMException.class, () -> {
			signatureUtil.decryptAmount(encryptedData);
		});
	}

	//@Test
	void testDecryptAmountExceptionHandling() throws Exception {
		String encryptedData = "InvalidEncryptedData";
		String secretKey = "Op5sTs3Nr7r9lCSJr2jN3qNyelrSEsO=";
		CRMException thrown = assertThrows(CRMException.class, () -> {
			signatureUtil.decryptAmount(encryptedData);
		});
		assertNotNull(thrown);
	}
}
