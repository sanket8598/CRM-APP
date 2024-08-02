package ai.rnt.crm.config;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebSocketService {

	private final SimpMessagingTemplate template;

    public void sendNotification(String destination, Object payload) {
        template.convertAndSend(destination, payload);
    }
}
