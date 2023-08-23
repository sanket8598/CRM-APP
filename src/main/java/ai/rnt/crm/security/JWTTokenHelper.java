package ai.rnt.crm.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ai.rnt.crm.dto.Role;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.KeyGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

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
public class JWTTokenHelper {

	private final EmployeeService service;
	private final KeyGenerator keyGenerator;

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
		return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
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

		return Jwts.builder().setHeaderParam("alg", "AES").setHeaderParam("enc", "A256GCM").setClaims(claims)
				.setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, getKey()).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public SecretKey getKey() {
		return keyGenerator.generateKey(secret);
	}
}
