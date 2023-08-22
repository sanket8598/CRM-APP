package ai.rnt.crm.security.config;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.rnt.crm.payloads.ApiError;
import ai.rnt.crm.payloads.JwtAuthResponse;
import ai.rnt.crm.payloads.JwtAuthResponse.JwtAuthResponseBuilder;
import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final CustomUserDetails customUserDetails;
	private final AuthenticationManager authenticationManager;
	private final JWTTokenHelper helper;

	/**
	 * {@inheritDoc}
	 *
	 * This method is called when user trying to log-in into application. when user
	 * hit login end-point then this method will execute and authenticate user using
	 * provided usename and password.
	 * 
	 * The spring securitiy's {@code authenticationManager.authenticate()} method
	 * will call loadUserByUsername method internally to load user details.<br>
	 * 
	 * @throws AuthenticationException <br>
	 *                                 {@link AuthenticationException} If unable to
	 *                                 authenticate user or provided credentials are
	 *                                 wrong<br>
	 * 
	 * @since version 1.0
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.info("Entered in the Login api..");
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try (ServletInputStream s = request.getInputStream()) {
			jsonNode = objectMapper.readTree(s);
			String username = String.valueOf(jsonNode.get("userId"));
			String password = String.valueOf(jsonNode.get("password"));
			UserDetails userDetail = customUserDetails.loadUserByUsername(username);
			if(nonNull(userDetail))
				return attemptAuthenticationHead(username, password, userDetail);
			log.info("Authenticating user. Username {}", username);
		}catch (Exception e) {
			log.info("Exception Occured While Login Api. {}", e.getLocalizedMessage());
		}
		return null;
	}
	
	private Authentication attemptAuthenticationHead(String username, String password, UserDetails userDetail) {
		try {
			// Check user has provided role or not
			log.info("Checking user role. Username {}", username);
			if (nonNull(userDetail) && nonNull(userDetail.getAuthorities())) {
				userDetail.getAuthorities().stream().map(String::valueOf).filter(r->r.equalsIgnoreCase("CRM_ADMIN") || r.equalsIgnoreCase("CRM_USER")).findAny()
						.orElseThrow(() -> new InsufficientAuthenticationException(
								"User has role not found."+userDetail.getUsername()));
			}
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (AuthenticationException e) {
		}
		return null;
	}
	/**
	 * This method will call if user is successfully authenticated and user
	 * credentials are correct. {@inheritDoc}
	 * 
	 * @since version 1.0
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		try {
			UserDetail user = (UserDetail) authResult.getPrincipal();
			JwtAuthResponseBuilder responseBuilder = JwtAuthResponse.builder().token(helper.generateToken(user));
				
			
			try (ServletOutputStream resOut = response.getOutputStream()) {
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(resOut, responseBuilder);
			}
			log.info("Authentication successfull. Username {}", user.getUsername());
		} catch (Exception e) {
			log.error("Error While successful authentication {} ",e.getMessage());
		}
	}

	/**
	 * This method will call if user is authentication failed and user credentials
	 * are wrong. {@inheritDoc}
	 * 
	 * @since version 1.0
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		log.error("unsuccessfulAuthentication......");
		try {
			ApiError err = new ApiError(UNAUTHORIZED, failed.getLocalizedMessage(), failed);
			request.setAttribute("err", err);
			err.setTimestamp(null);
		} catch (Exception e) {
			throw failed;
		}
	}
	
	
}
