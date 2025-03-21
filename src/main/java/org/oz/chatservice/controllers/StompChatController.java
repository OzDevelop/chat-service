package org.oz.chatservice.controllers;

import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oz.chatservice.dtos.ChatMessage;
import org.oz.chatservice.entities.Message;
import org.oz.chatservice.services.ChatService;
import org.oz.chatservice.vos.CustomOAuth2User;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompChatController {

    private final ChatService chatService;

    // 아래의 메서드를 통해 스톰프 메세지르 다룸.
    @MessageMapping("/chats/{chatroomId}") // 어떤 경로로 퍼블리시된 메시지를 라우팅할 건지 지정.
    @SendTo("/sub/chats/{chatroomId}")
    public ChatMessage handleMessage(Principal principal, @DestinationVariable Long chatroomId, @Payload Map<String, String> payload) {
        log.info("{} sent {} in {}", principal.getName(), payload, chatroomId );

        CustomOAuth2User user = (CustomOAuth2User)((OAuth2AuthenticationToken) principal).getPrincipal();

        Message message = chatService.saveMessage(user.getMember(), chatroomId, payload.get("message"));
        return new ChatMessage(principal.getName(), payload.get("message"));
    }
}
