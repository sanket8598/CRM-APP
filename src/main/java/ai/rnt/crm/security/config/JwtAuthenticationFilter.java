package ai.rnt.crm.security.config;

import static ai.rnt.crm.constants.SecurityConstant.TOKEN_PREFIX_BEARER;
import static ai.rnt.crm.security.AuthenticationUtil.ALLOW_URL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is the entryPoint and every request goes through this doFilterInternal method.
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final CustomUserDetails detailsService;

	private final JWTTokenHelper helper;

	/**
	 * Used Same contract as for {@code doFilter}, but guaranteed to be just invoked
	 * once per request within a single request thread. See
	 * {@link #shouldNotFilterAsyncDispatch()} for details.
	 * <p>
	 * Provides HttpServletRequest and HttpServletResponse arguments instead of the
	 * default ServletRequest and ServletResponse ones.
	 * 
	 * @since 23/08/2023
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (ALLOW_URL.test(request.getServletPath())) {
				filterChain.doFilter(request, response);
				return;
			} else {
				String requestTokenHeader = request.getHeader(AUTHORIZATION);
				if (Objects.isNull(requestTokenHeader))
					throw new MissingServletRequestPartException("AUTHORIZATION Header is missing");
				String userName;
				if (requestTokenHeader.startsWith(TOKEN_PREFIX_BEARER) && Objects.nonNull(requestTokenHeader)) {
					requestTokenHeader = requestTokenHeader.substring(7);
					userName = this.helper.extractUsername(requestTokenHeader);
					UserDetail loadUserByUsername = this.detailsService.loadUserByUsername(userName);
					if (Boolean.TRUE.equals(this.helper.validateToken(requestTokenHeader, loadUserByUsername))) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								requestTokenHeader, null, loadUserByUsername.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(loadUserByUsername);
						getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Got Excetion while checking request authorizations. Request: {}. Tokan: {}",
					request.getHeader(AUTHORIZATION));
		}
	}

}
