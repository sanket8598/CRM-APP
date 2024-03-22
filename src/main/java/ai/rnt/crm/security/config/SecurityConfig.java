package ai.rnt.crm.security.config;

import static ai.rnt.crm.security.AuthenticationUtil.ALLOW_URL;
import static ai.rnt.crm.security.AuthenticationUtil.PUBLIC_URLS;
import static org.springframework.security.config.Customizer.withDefaults;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:confidential.properties")
public class SecurityConfig implements WebMvcConfigurer {

	@Qualifier("handlerExceptionResolver")
	@Autowired
	private HandlerExceptionResolver exceptionResolver;
	private final CustomUserDetails customUserDetails;
	private final JWTAuthenticationEntryPoint authenticationEntryPoint;

	@Value("${csrf.token}")
	private String token;
	
	@Value("${allowed.origin}")
	private String allowedOrigin;
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http.cors(withDefaults());
		http.csrf(csrf -> {
			try {
				csrf
				.requireCsrfProtectionMatcher(new CsrfRequireMatcher())
				.csrfTokenRepository(new PerRequestCsrfTokenRepository(token)).and()
						// we can give give access to the api based on the role or using
						// e.g.antMatchers("/api/users/{path}").hasRole(null)
						.authorizeHttpRequests().antMatchers(PUBLIC_URLS).permitAll()
						.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(CorsUtils::isPreFlightRequest).permitAll().anyRequest().authenticated();
			} catch (Exception e) {
				log.error("error occurred in the securityFilterChain... {}", e.getMessage());
			}
		}).exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http.authenticationProvider(daoAuthenticationProvider());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	JwtAuthenticationFilter authenticationFilter() {
		return new JwtAuthenticationFilter(exceptionResolver);
	}

	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetails);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	/**
	 * This method will configure cross origin access to api's. {@inheritDoc}
	 * 
	 * @since version 1.0
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/" + "**").allowedOrigins(allowedOrigin).allowedMethods("*").allowedHeaders("*").exposedHeaders("*");
	}
}
class CsrfRequireMatcher implements RequestMatcher {
	
    @Override
    public boolean matches(HttpServletRequest request) {
        return !(ALLOW_URL.test(request.getServletPath()) || request.getMethod().equalsIgnoreCase("GET"));
    }
}
class PerRequestCsrfTokenRepository implements CsrfTokenRepository {
    private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    
    private String token;
    
    public PerRequestCsrfTokenRepository(String token) {
		this.token=token;
	}
    
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(DEFAULT_CSRF_HEADER_NAME, DEFAULT_CSRF_PARAMETER_NAME,token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return null;
    }
}