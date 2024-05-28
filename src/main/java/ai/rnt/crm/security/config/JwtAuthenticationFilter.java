package ai.rnt.crm.security.config;

import static ai.rnt.crm.constants.SecurityConstant.TOKEN_PREFIX_BEARER;
import static ai.rnt.crm.security.AuthenticationUtil.ALLOW_URL;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.UserDetail;
import ai.rnt.crm.util.JwtTokenDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is the entryPoint and every request goes through this
 * doFilterInternal method.
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 */

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private HandlerExceptionResolver exceptionResolver;

	@Autowired
	private CustomUserDetails detailsService;

	@Autowired
	private JWTTokenHelper helper;

	public JwtAuthenticationFilter(HandlerExceptionResolver exceptionResolver) {
		this.exceptionResolver = exceptionResolver;
	}

	/**
	 * This method is entrypoint for every request of the application.<br>
	 * {@inheritDoc}
	 * 
	 * @since version 1.0
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			log.info("entered inside the security entryPoint with...{}", request.getServletPath());
			if (ALLOW_URL.test(request.getServletPath())) {
				filterChain.doFilter(request, response);
				return;
			} else {
				String requestTokenHeader = request.getHeader(AUTHORIZATION);
				
				if (isNull(requestTokenHeader))
					throw new MissingServletRequestPartException("AUTHORIZATION Header is missing");
				String userName;
				if (nonNull(requestTokenHeader) && requestTokenHeader.startsWith(TOKEN_PREFIX_BEARER)) {
					requestTokenHeader = requestTokenHeader.substring(7);
					JsonNode json = new ObjectMapper().readTree(new JwtTokenDecoder().testDecodeJWT(requestTokenHeader));
					if (!request.getRemoteAddr().equalsIgnoreCase(json.get("ipAddress").toString().replace("\"", "")))
						throw new ResponseStatusException(UNAUTHORIZED, "Access Denied!!");
					userName = this.helper.extractUsername(requestTokenHeader);
					UserDetail loadUserByUsername = this.detailsService.loadUserByUsername(userName);
					if (TRUE.equals(this.helper.validateToken(requestTokenHeader, loadUserByUsername))) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								requestTokenHeader, null, loadUserByUsername.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(loadUserByUsername);
						getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			log.error("Got Excetion while checking request authorizations. Request: {} {} {}",
					request.getHeader(AUTHORIZATION), e.getClass(), e.getMessage());
			exceptionResolver.resolveException(request, response, null, e);
		}
	}
}
