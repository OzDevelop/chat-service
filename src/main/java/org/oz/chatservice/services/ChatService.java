package org.oz.chatservice.services;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oz.chatservice.entities.ChatRoom;
import org.oz.chatservice.entities.Member;
import org.oz.chatservice.entities.MemberChatroomMapping;
import org.oz.chatservice.repositories.ChatroomRepository;
import org.oz.chatservice.repositories.MemberChatroomMappingRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    final ChatroomRepository chatroomRepository;
    final MemberChatroomMappingRepository memberChatroomMappingRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(Member member, String title) {
        ChatRoom chatroom = ChatRoom.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();

        chatroom = chatroomRepository.save(chatroom);

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatroom)
                .build();

        memberChatroomMappingRepository.save(memberChatroomMapping);

        return chatroom;
    }

    // 채팅방 참여
    public boolean joinChatroom(Member member, Long chatroomId) {
        if ( memberChatroomMappingRepository.existsByChatroomIdAndMemberId(chatroomId, member.getId())) {
            log.info("이미 참여한 채팅방입니다.");
            return false;
        }

        ChatRoom chatRoom = chatroomRepository.findById(chatroomId).get();

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatRoom)
                .build();

        memberChatroomMapping =  memberChatroomMappingRepository.save(memberChatroomMapping);

        return true;

    }

    // 채팅방 나가기
    public boolean leaveChatroom(Member member, Long chatroomId) {
        if (!memberChatroomMappingRepository.existsByChatroomIdAndMemberId(chatroomId, member.getId())) {
            log.info("참여하지 않은 채팅방입니다.");
            return false;
        }

        memberChatroomMappingRepository.deleteByChatroomIdAndMemberId(chatroomId, member.getId());

        return true;
    }

    // 참여한 모든 채팅방 리스트 가져오기
    public List<ChatRoom> getAllChatrooms(Member member) {
        List<MemberChatroomMapping> memberChatroomMappingList = memberChatroomMappingRepository.findByAllByMemberId(member.getId());

        return memberChatroomMappingList.stream()
                .map(MemberChatroomMapping::getChatroom)
                .toList();
    }
}
