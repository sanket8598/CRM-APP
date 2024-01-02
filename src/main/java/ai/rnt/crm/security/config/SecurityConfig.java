package ai.rnt.crm.security.config;

import static ai.rnt.crm.security.AuthenticationUtil.PUBLIC_URLS;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
@SuppressWarnings("deprecation")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver exceptionResolver;
	private final CustomUserDetails customUserDetails;
	private final JWTAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults());
		http.csrf(csrf -> {
			try {
				csrf.disable().authorizeHttpRequests()
						// we can give give access to the api based on the role or using
						// e.g.antMatchers("/api/users/{path}").hasRole(null)
						.antMatchers(PUBLIC_URLS).permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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
		registry.addMapping("/" + "**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").exposedHeaders("*");
	}
	
}
