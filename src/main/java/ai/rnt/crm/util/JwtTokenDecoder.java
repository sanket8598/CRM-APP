package ai.rnt.crm.util;

import org.apache.tomcat.util.codec.binary.Base64;

public class JwtTokenDecoder {
	public String testDecodeJWT(String token) {
	return new String(new Base64(true).decode(token.split("\\.")[1]));
	}
}
