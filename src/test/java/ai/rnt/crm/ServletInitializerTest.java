package ai.rnt.crm;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ServletInitializerTest {

	@InjectMocks 
	ServletInitializer servletInitializer;
	@InjectMocks 
	CrmApplication crmApplication;
	
	@Autowired
	MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(servletInitializer).build();
	}
	
	
	 @Test
     void testConfigure() {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder();
        SpringApplicationBuilder result = servletInitializer.configure(springApplicationBuilder);
        assertSame(result.sources(CrmApplication.class), result);
    }

}
