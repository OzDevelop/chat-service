package org.oz.chatservice.controllers;

import java.security.Principal;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.oz.chatservice.dtos.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompChatController {

    // 아래의 메서드를 통해 스톰프 메세지르 다룸.
    @MessageMapping("/chats") // 어떤 경로로 퍼블리시된 메시지를 라우팅할 건지 지정.
    @SendTo("/sub/chats")
    public ChatMessage handleMessageA(@AuthenticationPrincipal Principal principal, @Payload Map<String, String> payload) {
        log.info("{} sent {}", principal.getName(), payload);

        return new ChatMessage(principal.getName(), payload.get("message"));
    }
}
