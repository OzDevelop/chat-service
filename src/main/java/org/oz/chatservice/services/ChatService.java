package org.oz.chatservice.services;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oz.chatservice.dtos.ChatroomDto;
import org.oz.chatservice.entities.Chatroom;
import org.oz.chatservice.entities.Member;
import org.oz.chatservice.entities.MemberChatroomMapping;
import org.oz.chatservice.entities.Message;
import org.oz.chatservice.repositories.ChatroomRepository;
import org.oz.chatservice.repositories.MemberChatroomMappingRepository;
import org.oz.chatservice.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    final ChatroomRepository chatroomRepository;
    final MemberChatroomMappingRepository memberChatroomMappingRepository;
    private final MessageRepository messageRepository;

    // 채팅방 생성
    public Chatroom createChatroom(Member member, String title) {
        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();

        chatroom = chatroomRepository.save(chatroom);

        MemberChatroomMapping memberChatroomMapping = chatroom.addMember(member);

        memberChatroomMappingRepository.save(memberChatroomMapping);

        return chatroom;
    }

    // 채팅방 참여
    public boolean joinChatroom(Member member, Long newChatroomId, Long currentChatroomId) {
        if (currentChatroomId != null) {
            updateLastCheckAt(member, currentChatroomId);
        }
        if ( memberChatroomMappingRepository.existsByChatroomIdAndMemberId(newChatroomId, member.getId())) {
            log.info("이미 참여한 채팅방입니다.");
            return false;
        }

        Chatroom chatRoom = chatroomRepository.findById(newChatroomId).get();

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatRoom)
                .build();

        memberChatroomMapping =  memberChatroomMappingRepository.save(memberChatroomMapping);

        return true;

    }

    private void updateLastCheckAt(Member member, Long currentChatroomId) {
        MemberChatroomMapping memberChatroomMapping = memberChatroomMappingRepository.findByMemberIdAndChatroomId(member.getId(), currentChatroomId)
                .get();
        memberChatroomMapping.updateLastCheckedAt();

        memberChatroomMappingRepository.save(memberChatroomMapping);
    }

    // 채팅방 나가기
    @Transactional
    public boolean leaveChatroom(Member member, Long chatroomId) {
        if (!memberChatroomMappingRepository.existsByChatroomIdAndMemberId(chatroomId, member.getId())) {
            log.info("참여하지 않은 채팅방입니다.");
            return false;
        }

        memberChatroomMappingRepository.deleteByChatroomIdAndMemberId(chatroomId, member.getId());

        return true;
    }

    // 참여한 모든 채팅방 리스트 가져오기
    public List<Chatroom> getChatroomList(Member member) {
        List<MemberChatroomMapping> memberChatroomMappingList = memberChatroomMappingRepository.findAllByMemberId(member.getId());

        return memberChatroomMappingList.stream()
                .map( memberChatroomMapping -> {
                    Chatroom chatroom = memberChatroomMapping.getChatroom();
                    chatroom.setHasNewMessage(messageRepository.existsByChatroomIdAndCreatedAtAfter(chatroom.getId(), memberChatroomMapping.getLastCheckedAt()));

                    return chatroom;
                })
                .toList();
    }

    // Member는 member 객체로 받고, chatroom은 id만 받는 이유는?
    public Message saveMessage(Member member, Long ChatroomId, String text) {
        Chatroom chatroom = chatroomRepository.findById(ChatroomId).get();

        // new 키워드가 아닌 빌더를 이용해 생성하는 이유??
        Message message = Message.builder()
                .text(text)
                .member(member)
                .chatroom(chatroom)
                .createdAt(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    // 특정 채팅방의 메시지 리스트 가져오기
    public List<Message> getMesssageList(Long chatroomId) {
        return messageRepository.findAllByChatroomId(chatroomId);
    }

    @Transactional
    public ChatroomDto getChatroom(Long chatroomId) {
        Chatroom chatroom =  chatroomRepository.findById(chatroomId).get();

        return ChatroomDto.from(chatroom);
    }

}
