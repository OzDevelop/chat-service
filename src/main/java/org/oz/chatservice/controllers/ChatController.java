package org.oz.chatservice.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oz.chatservice.dtos.ChatMessage;
import org.oz.chatservice.dtos.ChatroomDto;
import org.oz.chatservice.entities.Chatroom;
import org.oz.chatservice.entities.Message;
import org.oz.chatservice.services.ChatService;
import org.oz.chatservice.vos.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatroomDto createChatroom(@AuthenticationPrincipal CustomOAuth2User user, @RequestParam String title) {
        Chatroom chatRoom = chatService.createChatroom(user.getMember(), title);

        return ChatroomDto.from(chatRoom);
    }

    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatroomId) {
        return chatService.joinChatroom(user.getMember(), chatroomId);
    }

    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatroomId) {
        return chatService.leaveChatroom(user.getMember(), chatroomId);
    }

    @GetMapping
    public List<ChatroomDto> getChatroomList(@AuthenticationPrincipal CustomOAuth2User user) {
        List<Chatroom> chatRoomList = chatService.getAllChatrooms(user.getMember());

        return chatRoomList.stream()
                .map(ChatroomDto::from)
                .toList();
    }

    @GetMapping("/{chatroomId}/messages")
    public List<ChatMessage> getNessageList(@PathVariable Long chatroomId) {
        List<Message> messageList = chatService.getMesssageList(chatroomId);
        return messageList.stream()
                .map(message -> new ChatMessage(message.getMember().getNickName(), message.getText()))
                .toList();
    }


}
