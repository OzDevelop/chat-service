package org.oz.chatservice.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 웹 소켓 API를 사용하려면 웹소켓 핸들러를 구현해야 함.
 * 웹소켓 핸들러를 SpringBootApplication에서 사용할 수 있도록 Configuration 을 통해 설정해줘야함.
 * implements WebSocketHandler 을 하면 모든 내부 함수를 구현해주어야 하니,
 * extends TextWebSocketHandler을 통해 필요함수만 구현해줄 예정
 */

@Slf4j
@Component // 스프링에 Bean으로 등록
public class WebSocketChatHandler extends TextWebSocketHandler {

    // 웹소켓 클라이언트가 서버로 연결을 한 이후에 실행되는 코드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} connected", session.getId());

    }

    // 웹소켓 클라이언트에서 메세지가 왔을 때 그 메세지를 처리하는 로직 작성
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }


    // 서버에 접속해있던 웹소켓 클라이언트가 연결을 끊었을 때 처리하는 로직 작성
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} disconnected", session.getId());
    }
}
