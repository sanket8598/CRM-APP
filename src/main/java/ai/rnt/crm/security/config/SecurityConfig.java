package ai.rnt.crm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebMvc
@Slf4j
public class SecurityConfig implements WebMvcConfigurer{
	
   private final CustomUserDetails customUserDetails;
   private final JwtAuthenticationFilter authenticationFilter;
   private final JWTAuthenticationEntryPoint authenticationEntryPoint;
	
	private static final String[] PUBLIC_URLS= {"/api/v1/auth/**","/api/v1/auth/**"
			,"/v3/api-docs",
			"/v2/api-docs",
			"swagger-resources/**",
			"swagger-ui/**",
			"/webjars/**"
	};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> {
					try {
						csrf
						        .disable()
						        .authorizeHttpRequests().
						        //we can give give access to the api based on the role or using 
						        //e.g.antMatchers("/api/users/{path}").hasRole(null)
						        antMatchers(PUBLIC_URLS).permitAll()
						        .anyRequest()
						        .authenticated();
					} catch (Exception e) {
						log.error("error occurred in the ");
					}
				})
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		  http.addFilterBefore(authenticationFilter,
				  UsernamePasswordAuthenticationFilter.class);
		  
		  http.authenticationProvider(daoAuthenticationProvider());
		  
		  return http.build();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

    @Bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception 
	{
		return authenticationConfiguration.getAuthenticationManager();
		}

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetails);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
    

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/" + "**").allowedOrigins("*")
		.allowedMethods("*").allowedHeaders("*").exposedHeaders("*");
	}
    
    
	
}
