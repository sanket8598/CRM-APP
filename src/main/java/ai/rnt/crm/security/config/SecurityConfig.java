package ai.rnt.crm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@AllArgsConstructor
public class SecurityConfig{
	
   private CustomUserDetails customUserDetails;
   private JwtAuthenticationFilter authenticationFilter;
   private JWTAuthenticationEntryPoint authenticationEntryPoint;
	
	private static final String[] PUBLIC_URLS= {"/api/v1/auth/**"
			,"/v3/api-docs",
			"/v2/api-docs",
			"swagger-resources/**",
			"swagger-ui/**",
			"/webjars/**"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		  .csrf() 
		   .disable() 
		   .authorizeHttpRequests().
		   //we can give give access to the api based on the role or using 
		   //e.g.antMatchers("/api/users/{path}").hasRole(null)
		  antMatchers(PUBLIC_URLS).permitAll() 
		  .anyRequest() 
		  .authenticated() 
		  .and()
		  .exceptionHandling()
		  .authenticationEntryPoint(authenticationEntryPoint)
		  .and() 
		  .sessionManagement()
		  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		  http.addFilterBefore(authenticationFilter,
				  UsernamePasswordAuthenticationFilter.class);
		  
		  http.authenticationProvider(daoAuthenticationProvider());
		  
		  return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception 
	{
		return authenticationConfiguration.getAuthenticationManager();
		}
	
	@Bean 
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetails);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
}
