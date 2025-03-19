package org.oz.chatservice.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker   // STOMP 기능 활성화
@Configuration
public class StompConfiguration implements WebSocketMessageBrokerConfigurer {

    // 웹 소켓 클라이언트가 어떠한 경로로 서버로 접근해야 하는지에 대한 엔드포인트 지정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("stomp/chats");
    }


    // 메세지 브로커로서의 역할을 하기 위해 클라이언트에서 메세지를 발행하고, 브로커로부터 전달되는 메시지를 받기 위해 구독을 신챙해야 하는데,
    // 그 경로들을 지정하는 것.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 퍼블리시하는 url 지정
        registry.setApplicationDestinationPrefixes("/pub");
        // 메시지 구독 신청
        registry.enableSimpleBroker("/sub");
    }
}
