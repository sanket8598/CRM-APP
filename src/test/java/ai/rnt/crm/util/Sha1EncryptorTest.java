package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Sha1EncryptorTest {

	@Test
    void testEncryptThisString() {
        String input = "password";
        String expectedHash = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";
        Sha1Encryptor encryptor = new Sha1Encryptor("SHA-1");
        String actualHash = encryptor.encryptThisString(input);
        assertEquals(expectedHash, actualHash, "SHA-1 hash should match");
    }

    @Test
    void testEncryptThisStringWithNullInput() {
        String input = null;
        Sha1Encryptor encryptor = new Sha1Encryptor("SHA-1");
        String actualHash = encryptor.encryptThisString(input);
        assertNull(actualHash, "Hash should be null for null input");
    }
}
