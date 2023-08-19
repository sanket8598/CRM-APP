package ai.rnt.crm.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.payloads.JwtAuthRequest;
import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.config.CustomUserDetails;
import ai.rnt.crm.util.Sha1Encryptor;
import lombok.AllArgsConstructor;
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
	public ResponseEntity<Map<String, Object>> createAuthenticationToken(@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
		log.info("calling login api...");
		Map<String, Object> map = new HashMap<>();
		try {
			jwtAuthRequest.setPassword(Sha1Encryptor.encryptThisString(jwtAuthRequest.getPassword()));
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUserId(),
					jwtAuthRequest.getPassword()));
			UserDetails loadUserByUsername = customUserDetails.loadUserByUsername(jwtAuthRequest.getUserId());
			String token = helper.generateToken(loadUserByUsername);
			if (token != null) {
				map.put("success", true);
				map.put("token", token);
			}
		} catch (Exception e) {
			log.error("Error Occured while login.. {}",e);
			if(e.getClass().equals(BadCredentialsException.class)) {
			map.put("success", false);
			map.put("message", "Your Credentials are not Valid");
			}
		}
		return ResponseEntity.ok(map);
	}

}
