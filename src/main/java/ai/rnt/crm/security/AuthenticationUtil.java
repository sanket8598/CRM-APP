package ai.rnt.crm.security;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.NoArgsConstructor;

/**
 * This class is used to get the required endpoint through predicate and functions.
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 */

@NoArgsConstructor(access = PRIVATE)
public class AuthenticationUtil {
	
	
	
	public static final Supplier<List<String>> PUBLIC_APIS = () -> {
		List<String> api = new ArrayList<>();
		api.add("/api/v1/auth/login"); 
		api.add("/send-notification"); 
		api.add("/api/v1/auth/tokenparse"); 
		api.add("/v3/api-docs"); 
		api.add("swagger-resources/"); 
		api.add("/swagger-ui/index.html"); 
		api.add("/swagger-ui.html"); 
		api.add("swagger-resources/"); 
		api.add("swagger-ui/index.html"); 
		api.add("swagger-ui.html"); 
		api.add("/webjars/**"); 
		return api;
		
	};
	
	public static final String[] PUBLIC_URLS = { "/send-notification","/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
			"swagger-resources/**", "swagger-ui/**", "/webjars/**" };
	public static final String[] ADMIN_URLS = { "/api/v1/admin/**" };
	public static final String[] USER_URLS = { "/api/v1/user/**"};
	
	

	/**
	 * This Predicate return true if passed API URL is public.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> ALLOW_URL = s -> {
		if (isNull(s))
			return false;
		return PUBLIC_APIS.get().stream().anyMatch(s::startsWith);
	};
}
