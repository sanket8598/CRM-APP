package ai.rnt.crm.security.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	 private static final String CSRF_HEADER = "X-CSRF-TOKEN";

	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}

	private List<SecurityReference> securityReferences() {
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scope }),new SecurityReference("CSRF-TOKEN", new AuthorizationScope[] { scope }));
	}


    private SecurityScheme csrfTokenKey() {
        return new springfox.documentation.service.ApiKey("CSRF-TOKEN", CSRF_HEADER, "header");
    }
	@Bean
	Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys(),csrfTokenKey())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("CRM Application :REST API", "This project is developed by RNT", "1.0", "Terms And Service",
				new Contact("Sanket", "www.rnt.ai", "sanketwakankar8@gmail.com"), "Licennse of APi", "API lincense URL",
				Collections.emptyList());
	}

}
