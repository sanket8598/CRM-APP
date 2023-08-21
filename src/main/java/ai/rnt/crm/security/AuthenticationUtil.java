package ai.rnt.crm.security;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AuthenticationUtil {
	
	/**
	 * This Predicate return true if passed API URL is public.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> ALLOW_URL = s -> {
		if (isNull(s))
			return false;
		if (AuthenticationUtil.PUBLIC_APIS.get().stream().anyMatch(s::startsWith))
			return true;
		return false;
	};
	
	private static final Supplier<List<String>> PUBLIC_APIS = () -> {
		List<String> api = new ArrayList<>();
		api.add("/api/v1/auth/login"); 
		api.add("/api/v1/auth/tokenparse"); 
		return api;
	};

}
