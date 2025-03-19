package org.oz.chatservice.configs;

import lombok.RequiredArgsConstructor;
import org.oz.chatservice.handlers.WebSocketChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocketChatHandler를 어플레케이션에 등록하는 Configuration
 */

@EnableWebSocket // 이 클래스를 통해 우리 앱이 웹소켓을 쓸꺼다~
@Configuration
@RequiredArgsConstructor // 생성자 자동 생성
public class WebSocketConfiguration implements WebSocketConfigurer {

    final WebSocketChatHandler webSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 어떤 경로로 서버에 접근했을 때 해당 핸들로럴 적용할 것인지에 대한 paths
        registry.addHandler(webSocketChatHandler, "/ws/chats");

    }
}

