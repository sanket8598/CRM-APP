package ai.rnt.crm.util;

import java.util.Objects;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class XSSUtil {
	 private XSSUtil(){}
	 private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
		        .toFactory();

		    public static String sanitize(String input) {
		        return POLICY.sanitize(input);
		    }
		    
  public static String removeGarbageValue(String str) {
	  return Objects.nonNull(str)?str.replaceAll("[^\\x00-\\x7F]", ""):str;//to remove garbage value
  }
}
