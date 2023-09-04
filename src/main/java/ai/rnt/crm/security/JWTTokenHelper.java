package ai.rnt.crm.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ai.rnt.crm.constants.EncryptionAlgoConstants;
import ai.rnt.crm.dto.Role;
import ai.rnt.crm.service.EmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to get the information from DB and set it in the JWT
 * token.
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JWTTokenHelper {

	private final EmployeeService service;
	@Value("${jwt.secret.key}")
	private String secret;

	public static final long JWT_TOKEN_VALIDITY = 100 * 60 * 60;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		service.getEmployeeByUserId(userDetails.getUsername()).ifPresent(emp -> {
			claims.put("fullName", emp.getFirstName() + " " + emp.getLastName());
			claims.put("Role",
					emp.getEmployeeRole().stream().filter(r -> r.getRoleName().equalsIgnoreCase("Policy Admin"))
							.map(Role::getRoleName).findAny().orElse("CRM USER"));
		});
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims)
				.setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public KeyPair getKeyPair() {
		try {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EncryptionAlgoConstants.RSA);
        keyPairGenerator.initialize(2048);
       return keyPairGenerator.generateKeyPair();
		}catch(Exception e) {
			log.error("error occured while getting the Keys.{} ",e.getMessage());
		}
		return null;
	}
}
