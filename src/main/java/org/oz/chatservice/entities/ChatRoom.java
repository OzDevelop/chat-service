package org.oz.chatservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class ChatRoom {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    @Id
    Long id;


    String title;

    /** @ManyToMany를 사용하지 않는 이유.
     * 1. 채팅방에는 여러명의 멤버가 있을 수 있지만, 한 멤버는 여러개의 채팅방에 참여할 수 있음. -> 다대다
     * 2. 다대다 관계는 중간 테이블이 필요함. OneToMany, ManyToOne 형태로 매핑 엔티티를 정의해주고
     * 매핑 엔티티를 통해 ManyToMany를 구현해주는 것이 좋음.
     *
     * 왜?!?!?! 노션에 정리하기.
     */
//    @ManyToMany
    @OneToMany(mappedBy = "chatroom")
    Set<MemberChatroomMapping> memberChatroomMappings;


    LocalDateTime createdAt;


}

