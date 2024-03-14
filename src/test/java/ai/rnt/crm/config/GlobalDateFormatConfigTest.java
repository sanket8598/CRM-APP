package ai.rnt.crm.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class GlobalDateFormatConfigTest {

	
	@Test
    void jsonCustomizer_ShouldConfigureDateFormat() {
        @SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(GlobalDateFormatConfig.class);
        context.refresh();
        Jackson2ObjectMapperBuilderCustomizer customizer =
                context.getBean(GlobalDateFormatConfig.class).jsonCustomizer();
        Jackson2ObjectMapperBuilder objectMapperBuilder = new Jackson2ObjectMapperBuilder();
        customizer.customize(objectMapperBuilder);
        ObjectMapper objectMapper = objectMapperBuilder.build();
        objectMapper.registerModule(new JavaTimeModule());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate localDate = LocalDate.of(2022, 3, 18);
        LocalDateTime localDateTime = LocalDateTime.of(2022, 3, 18, 12, 30, 15);
        String expectedFormattedDate = localDate.format(dateFormatter);
        String expectedFormattedDateTime = localDateTime.format(dateTimeFormatter);
        String serializedDate = objectMapper.convertValue(localDate, String.class);
        String serializedDateTime = objectMapper.convertValue(localDateTime, String.class);
        assertEquals(expectedFormattedDate , serializedDate);
        assertEquals(expectedFormattedDateTime, serializedDateTime);
    }
}
