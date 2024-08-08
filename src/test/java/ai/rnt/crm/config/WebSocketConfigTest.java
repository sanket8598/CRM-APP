package ai.rnt.crm.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootTest(classes = WebSocketConfig.class)
public class WebSocketConfigTest {

    @Autowired
    private WebSocketMessageBrokerConfigurer webSocketMessageBrokerConfigurer;

    @Test
    void testConfigureMessageBroker() {
        MessageBrokerRegistry config = mock(MessageBrokerRegistry.class);
        
        webSocketMessageBrokerConfigurer.configureMessageBroker(config);
        
        verify(config).enableSimpleBroker("/crm");
        verify(config).setApplicationDestinationPrefixes("/app");
    }

}
