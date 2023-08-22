package ai.rnt.crm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ai.rnt.crm.security.JWTTokenHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebMvc
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	private final CustomUserDetails customUserDetails;
	private final JWTAuthenticationEntryPoint authenticationEntryPoint;
	private final JWTTokenHelper helper;

	private static final String[] PUBLIC_URLS = { "/api/v1/auth/**", "/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
			"swagger-resources/**", "swagger-ui/**", "/webjars/**" };



	/**
	 * {@inheritDoc}
	 *
	 * This method will configure spring security. like api security and role base access of apis.
	 * @since version 1.0
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Configuring HttpSecurity..");
		http.csrf(csrf -> {
			try {
				csrf.disable().authorizeHttpRequests()
				// we can give give access to the api based on the role or using
				// e.g.antMatchers("/api/users/{path}").hasRole(null)
						.antMatchers(PUBLIC_URLS).permitAll()
				        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
		                .anyRequest().authenticated();
			} catch (Exception e) {
				log.error("error occurred in the ");
			}
		}).exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPoint))
		.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// Set API security Don't change position of method call
		//setApiSecurity(http);
		JwtAuthorizationFilter authenticationFilter = new JwtAuthorizationFilter(customUserDetails, authenticationManager(), helper);
		// Set login end-point
		authenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
		// Add authentication filter to authenticate user
		http.addFilter(authenticationFilter);
		// Add authorization filter to check user authority
		http.addFilterBefore(new JwtAuthenticationFilter(customUserDetails,helper), UsernamePasswordAuthenticationFilter.class);
		
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/" + "**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").exposedHeaders("*");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This method will configure authentication manager. used by spring security to
	 * authenticate user.
	 * 
	 * @since version 1.0
	 */
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/**
	 * {@inheritDoc}
	 *
	 * This method will configure UserDetailsService interface and password encoder.
	 * UserDetailsService contain loadUserByUsername method which is implemented by
	 * UserServiceImpl class. AuthenticationManager will call this method to load
	 * user details form our application.
	 * 
	 * @since version 1.0
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Configuring AuthenticationManagerBuilder..");
		auth.userDetailsService(customUserDetails).passwordEncoder(passwordEncoder());
	}

	/**
	 * {@inheritDoc}
	 *
	 * This method will configure web security.
	 * 
	 * @since version 1.0
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info("Configuring WebSecurity..");
		web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
		// web.ignoring().mvcMatchers(HttpMethod.GET, apis.apply(OTHER));
	}

	/**
	 * This method set api access role wise
	 * 
	 * @since version 1.0
	 *//*
		 * private void setApiSecurity(HttpSecurity http) throws Exception {
		 * http.authorizeRequests().antMatchers(apis.apply(OTHER)).permitAll();
		 * http.authorizeRequests().antMatchers(apis.apply(ANONYMOUS)).anonymous();
		 * http.authorizeRequests()
		 * .antMatchers(apis.apply(ROLE_ADMIN)).hasAnyAuthority(ROLE_ADMIN.val())
		 * .antMatchers(apis.apply(ROLE_DOCTOR)).hasAnyAuthority(ROLE_ADMIN.val(),
		 * ROLE_DOCTOR.val())
		 * .antMatchers(apis.apply(ROLE_PATIENT)).hasAnyAuthority(ROLE_ADMIN.val(),
		 * ROLE_DOCTOR.val(), ROLE_PATIENT.val());
		 * http.authorizeRequests().anyRequest().authenticated(); }
		 */

}
