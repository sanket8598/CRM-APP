package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.AUTH;
import static ai.rnt.crm.constants.ApiConstants.GET_ADMIN_AND_USER;
import static ai.rnt.crm.constants.ApiConstants.GET_ALL_MAIL_ID;
import static ai.rnt.crm.constants.ApiConstants.LOGIN;
import static ai.rnt.crm.constants.ApiConstants.TOKENPARSE;
import static ai.rnt.crm.constants.CRMConstants.EMAIL_ID;
import static ai.rnt.crm.constants.CRMConstants.FULL_NAME;
import static ai.rnt.crm.constants.CRMConstants.ROLE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.CRMConstants.TOKEN;
import static ai.rnt.crm.constants.RoleConstants.CHECK_ADMIN_ACCESS;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;
import static ai.rnt.crm.util.Sha1Encryptor.encryptThisString;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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
import ai.rnt.crm.util.PhoneNumberValidateApi;
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
	private final PhoneNumberValidateApi api;
	private final JWTTokenHelper helper;

	@PostMapping(LOGIN)
	public ResponseEntity<JwtAuthResponse> createAuthenticationToken(
			@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
		log.info("calling login api...");
		try {
			if (!jwtAuthRequest.isFromCorp())
				jwtAuthRequest.setPassword(encryptThisString(jwtAuthRequest.getPassword()));
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUserId(), jwtAuthRequest.getPassword()));
			String token = helper.generateToken(customUserDetails.loadUserByUsername(jwtAuthRequest.getUserId()));
			if (nonNull(token))
				return new ResponseEntity<>(JwtAuthResponse.builder().status(true).token(token).build(), OK);
			return new ResponseEntity<>(JwtAuthResponse.builder().status(false).token(null).build(), NO_CONTENT);
		} catch (Exception e) {
			log.error("Error Occured while login.. {}", e.getLocalizedMessage());
			throw new CRMException(e);
		}

	}

	@PostMapping(TOKENPARSE)
	public ResponseEntity<Map<String, Object>> tokenDecode(
			@RequestBody @NotEmpty(message = "body should not be empty!!") Map<String, String> token) {
		try {
			if (isNull(token.get(TOKEN)))
				throw new ResponseStatusException(BAD_REQUEST, "token cannot be null");
			if (token.get(TOKEN).trim().length() <= 0)
				throw new ResponseStatusException(BAD_REQUEST, "token cannot be empty");

			log.info("token inside tokenParse...{}", token.get(TOKEN));
			JsonNode json = new ObjectMapper().readTree(new JwtTokenDecoder().testDecodeJWT(token.get(TOKEN)));
			Map<String, Object> map = new LinkedHashMap<>();
			map.put(FULL_NAME, json.get(FULL_NAME));
			map.put(ROLE, json.get(ROLE));
			map.put(STAFF_ID, json.get(STAFF_ID));
			map.put(EMAIL_ID, json.get(EMAIL_ID));
			return ok(map);
		} catch (Exception e) {
			log.error("error occured while decoding the token.. {}", e.getLocalizedMessage());
			throw new CRMException(e);
		}
	}

	@GetMapping(value = { GET_ADMIN_AND_USER, GET_ALL_MAIL_ID })
	@PreAuthorize(CHECK_BOTH_ACCESS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAdminAndUser(
			@PathVariable(name = "email", required = false) String mail) {
		return employeeService.getAdminAndUser(mail);
	}
	
	@GetMapping("/users")
	@PreAuthorize(CHECK_ADMIN_ACCESS)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCRMUser() {
		return employeeService.getCRMUser();
	}

	@GetMapping("/phoneNum/{phoneNum}")
	public Map<String, Object> validatePhoneNo(@PathVariable String phoneNum) {
		return api.checkPhoneNumberInfo(phoneNum);
	}
}
