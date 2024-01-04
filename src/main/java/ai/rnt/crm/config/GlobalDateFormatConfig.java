package ai.rnt.crm.config;

import static java.time.format.DateTimeFormatter.ofPattern;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class GlobalDateFormatConfig {
	
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder->{
			builder.serializers(new LocalDateSerializer(ofPattern("yyyy-MM-dd")));
			builder.serializers(new LocalDateTimeSerializer(ofPattern("yyyy-MM-dd HH:mm:ss")));
		};
	}

}
