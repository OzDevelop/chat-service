package org.oz.chatservice.dtos;

import java.time.LocalDateTime;
import org.oz.chatservice.entities.Chatroom;

public record ChatroomDto(
        Long id,
        String title,
        Integer memberCount,
        LocalDateTime createdAt) {

    public static ChatroomDto from(Chatroom chatRoom) {
        return new ChatroomDto(chatRoom.getId(), chatRoom.getTitle(), chatRoom.getMemberChatroomMappingSet().size(), chatRoom.getCreatedAt());
    }
}
