package ai.rnt.crm.util;

import org.apache.tomcat.util.codec.binary.Base64;

public class JwtTokenDecoder {
	public String testDecodeJWT(String token) {
	String[] tockenSplit = token.split("\\.");
	System.out.println(tockenSplit[1]);
	return new String(new Base64(true).decode(tockenSplit[1]));

		
	}
}
