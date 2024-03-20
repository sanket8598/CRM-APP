package ai.rnt.crm.security.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

class SwaggerConfigurationTest {

	@Autowired
	private ApplicationContext context;
	
	//@Test
	void apiInfoConfiguration() {
		ApiInfo expectedApiInfo = new ApiInfoBuilder().title("CRM Application :REST API")
				.description("This project is developed by RNT").version("1.0").termsOfServiceUrl("Terms And Service")
				.contact(new Contact("Sanket", "www.rnt.ai", "sanketwakankar8@gmail.com")).license("Licennse of APi")
				.licenseUrl("API lincense URL").build();
		SwaggerConfiguration swaggerConfiguration = context.getBean(SwaggerConfiguration.class);
		assertNotNull(expectedApiInfo);
	}
	
}
