package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.AUTH;
import static ai.rnt.crm.constants.ApiConstants.GET_ADMIN_AND_USER;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_MAIL_ID;
import static ai.rnt.crm.constants.ApiConstants.LOGIN;
import static ai.rnt.crm.constants.ApiConstants.TOKENPARSE;
import static ai.rnt.crm.constants.EncryptionAlgoConstants.RSA;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.payloads.JwtAuthRequest;
import ai.rnt.crm.payloads.JwtAuthResponse;
import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.config.CustomUserDetails;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.JwtTokenDecoder;
import ai.rnt.crm.util.RSAToJwtDecoder;
import ai.rnt.crm.util.Sha1Encryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(AUTH)
@Slf4j
@RequiredArgsConstructor
@Validated
public class LoginController {

	private final AuthenticationManager authenticationManager;

	private final EmployeeService employeeService;

	private final CustomUserDetails customUserDetails;
	private final JWTTokenHelper helper;
	public static Map<String, PrivateKey> keystore = new HashMap<>();

	@PostMapping(LOGIN)
	public ResponseEntity<JwtAuthResponse> createAuthenticationToken(
			@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
		log.info("calling login api...");
		try {
			jwtAuthRequest.setPassword(Sha1Encryptor.encryptThisString(jwtAuthRequest.getPassword()));
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUserId(), jwtAuthRequest.getPassword()));
			String token = helper.generateToken(customUserDetails.loadUserByUsername(jwtAuthRequest.getUserId()));
			if (nonNull(token)) {
				KeyPair keyPair = helper.getKeyPair();
				Cipher cipher = Cipher.getInstance(RSA);
				cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
				String newToken = Base64.getEncoder().encodeToString(cipher.doFinal(token.getBytes()));
				keystore.put(newToken, keyPair.getPrivate());
				return new ResponseEntity<>(JwtAuthResponse.builder().status(true).token(newToken).build(),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(JwtAuthResponse.builder().status(false).token(null).build(),
					HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			log.error("Error Occured while login.. {}", e.getLocalizedMessage());
			throw new CRMException(e);
		}

	}

	@PostMapping(TOKENPARSE)
	public ResponseEntity<Map<String, Object>> tokenDecode(
			@RequestBody @NotEmpty(message = "body should not be empty!!") Map<String, String> token) {
		try {

			if (isNull(token.get("token")))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "token cannot be null");
			if (token.get("token").trim().length() <= 0)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "token cannot be empty");

			log.info("token inside tokenParse...{}", token.get("token"));
			String rsaToJwtDecoder = RSAToJwtDecoder.rsaToJwtDecoder(token.get("token"));
			log.info("token after RSA inside tokenParse...{}", rsaToJwtDecoder);
			JsonNode json = new ObjectMapper().readTree(new JwtTokenDecoder().testDecodeJWT(rsaToJwtDecoder));
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("fullName", json.get("fullName"));
			map.put("role", json.get("Role"));
			map.put("staffId", json.get("StaffId"));
			map.put("emailId", json.get("EmailId"));
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			log.error("error occured while decoding the token.. {}", e.getLocalizedMessage());
			throw new CRMException(e);
		}
	}

	@GetMapping(value = {GET_ADMIN_AND_USER,GET_ALL_MAIL_ID})
	@PreAuthorize(CHECK_BOTH_ACCESS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAdminAndUser(
	@PathVariable(name = "email", required = false) String mail) {
		return employeeService.getAdminAndUser(mail);
	}
}
