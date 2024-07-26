package ai.rnt.crm.util;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;
/**
 * @author Sanket Wakankar
 * @since 20/02/2024
 * @version 1.0
 *
 */
@PropertySource("classpath:confidential.properties")
@Getter
@Setter
public class PropertyUtil {
	
	@Value("${email.userName}")
	protected String userName;
	
	@Value("${email.password}")
	protected String password;
	
	protected static final Properties PROPERTIES = new Properties();
	protected static final String HOST = "smtp.office365.com";
	//"smtp.zoho.com"
	
	static {
		PROPERTIES.put("mail.smtp.host", HOST);
		PROPERTIES.put("mail.smtp.port", "587");
		PROPERTIES.put("mail.smtp.auth", true);
		PROPERTIES.put("mail.smtp.starttls.enable", true);
	}
}
