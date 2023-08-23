package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.AUTH;
import static ai.rnt.crm.constants.ApiConstants.LOGIN;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.payloads.JwtAuthRequest;
import ai.rnt.crm.payloads.JwtAuthResponse;
import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.UserDetail;
import ai.rnt.crm.security.config.CustomUserDetails;
import ai.rnt.crm.util.JwtTokenDecoder;
import ai.rnt.crm.util.Sha1Encryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(AUTH)
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

	private final AuthenticationManager authenticationManager;

	private final CustomUserDetails customUserDetails;
	private final JWTTokenHelper helper;

	@PostMapping(LOGIN)
	public ResponseEntity<JwtAuthResponse> createAuthenticationToken(
			@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
		log.info("calling login api...");
		try {
			jwtAuthRequest.setPassword(Sha1Encryptor.encryptThisString(jwtAuthRequest.getPassword()));
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUserId(), jwtAuthRequest.getPassword()));
			UserDetail loadUserByUsername = customUserDetails.loadUserByUsername(jwtAuthRequest.getUserId());
			String token = helper.generateToken(loadUserByUsername);
			if (Objects.nonNull(token))
				return new ResponseEntity<>(JwtAuthResponse.builder().status(true).token(token).build(), HttpStatus.OK);
			return new ResponseEntity<>(JwtAuthResponse.builder().status(false).token(null).build(),
					HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			log.error("Error Occured while login.. {}", e.getLocalizedMessage());
			throw new CRMException(e);
		}

	}
	
	@PostMapping("/tokenparse")
	public ResponseEntity<Map<String, Object>> tokenDecode(@RequestBody String token) {
		Map<String, Object> map = null;
		try {
			map = new LinkedHashMap<>();
			JwtTokenDecoder jtd = new JwtTokenDecoder();
			String decodedToken = jtd.testDecodeJWT(token);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode json = objectMapper.readTree(decodedToken);
			map.put("fullName", json.get("fullName"));
			map.put("Role", json.get("Role"));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(map);
	}

}
