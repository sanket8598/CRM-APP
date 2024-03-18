package ai.rnt.crm.security;

import static ai.rnt.crm.constants.CRMConstants.EMAIL_ID;
import static ai.rnt.crm.constants.CRMConstants.FULL_NAME;
import static ai.rnt.crm.constants.CRMConstants.ROLE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static java.util.Objects.nonNull;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ai.rnt.crm.constants.EncryptionAlgoConstants;
import ai.rnt.crm.dto.Role;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.RoleUtil;
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
	

	public static final long JWT_TOKEN_VALIDITY = 5000L * 60 * 60; //3 hr
	// Singleton instance of KeyPair
    private static final KeyPair keyPair = generateECKeyPair();
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
		return Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		service.getEmployeeByUserId(userDetails.getUsername()).ifPresent(emp -> {
			claims.put(FULL_NAME, emp.getFirstName() + " " + emp.getLastName());
			claims.put(STAFF_ID, emp.getStaffId());
			claims.put(EMAIL_ID, emp.getEmailID());
			claims.put(ROLE, RoleUtil
					.getSingleRole(emp.getEmployeeRole().stream().map(Role::getRoleName).collect(Collectors.toList())));
		});
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.ES512,keyPair.getPrivate()).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public KeyPair getKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EncryptionAlgoConstants.RSA);
			keyPairGenerator.initialize(4096);
			return keyPairGenerator.generateKeyPair();
		} catch (Exception e) {
			log.error("error occured while getting the Keys.{} ", e.getMessage());
		}
		return null;
	}

	public String getfullNameOfLoggedInUser(String token) {
		return nonNull(this.extractAllClaims(token)) ? this.extractAllClaims(token).get(FULL_NAME).toString() : null;
	}
	
	private static KeyPair generateECKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp521r1");
            keyPairGenerator.initialize(ecGenParameterSpec);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Error generating key pair", e);
        }
    }
	
}
