package ai.rnt.crm.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ai.rnt.crm.security.JWTTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private CustomUserDetails detailsService;

	private JWTTokenHelper helper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1.get token
		String reqToken = request.getHeader("Authorization");

		String userName = null;
		String token = null;
		if (request != null && (reqToken != null && reqToken.startsWith("Bearer"))) {
			token = reqToken.substring(7);
			try {
				userName = this.helper.extractUsername(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has Expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT Token");
			}
		} else {
			System.out.println("Jwt Token Does not begin with Bearer");
		}

		// once we get the token, now validate..
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails loadUserByUsername = this.detailsService.loadUserByUsername(userName);
			if (Boolean.TRUE.equals(this.helper.validateToken(token, loadUserByUsername))) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(token,
						null, loadUserByUsername.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			} else
				System.out.println("Invalid Jwt Token");

		} else {
			System.out.println("username is null or context is not null");
		}
		filterChain.doFilter(request, response);
	}

}
