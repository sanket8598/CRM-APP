package ai.rnt.crm.config;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class WebSocketServiceTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private WebSocketService webSocketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification() {
        String destination = "/topic/notifications";
        String payload = "Test Notification";

        webSocketService.sendNotification(destination, payload);

        verify(simpMessagingTemplate, times(1)).convertAndSend(destination, payload);
    }
}
