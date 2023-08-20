package ai.rnt.crm.restcontroller;

import java.security.cert.CollectionCertStoreParameters;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.payloads.JwtAuthRequest;
import ai.rnt.crm.payloads.JwtAuthResponse;
import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.config.CustomUserDetails;
import ai.rnt.crm.util.Sha1Encryptor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
@AllArgsConstructor
public class LoginController {

	private AuthenticationManager authenticationManager;

	private CustomUserDetails customUserDetails;
	private JWTTokenHelper helper;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createAuthenticationToken(
			@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
		log.info("calling login api...");
		try {
			jwtAuthRequest.setPassword(Sha1Encryptor.encryptThisString(jwtAuthRequest.getPassword()));
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUserId(), jwtAuthRequest.getPassword()));
			UserDetails loadUserByUsername = customUserDetails.loadUserByUsername(jwtAuthRequest.getUserId());
			String token = helper.generateToken(loadUserByUsername);
			if (Objects.nonNull(token))
				return new ResponseEntity<JwtAuthResponse>(JwtAuthResponse.builder().status(true).token(token).build(),
						HttpStatus.OK);
			return new ResponseEntity<JwtAuthResponse>(JwtAuthResponse.builder().status(false).token(null).build(),
					HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			log.error("Error Occured while login.. {}", e.getLocalizedMessage());
			throw new CRMException(e);
		}

	}

}
