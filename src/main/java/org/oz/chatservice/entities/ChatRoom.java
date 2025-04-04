package org.oz.chatservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Chatroom {

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
    Set<MemberChatroomMapping> memberChatroomMappingSet;

    @Transient // 엔티티 속성은 테이블 컬럼으로 맵핌되게끔 되어 있는데 @Transent가 붙은 속성은 테이블에 적용되지 않음.
    Boolean hasNewMessage;


    // Entity에서 setter 써도 괜찮아?
    public void setHasNewMessage(Boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    LocalDateTime createdAt;

    public MemberChatroomMapping addMember(Member member) {
        if (this.getMemberChatroomMappingSet() == null) {
            this.memberChatroomMappingSet = new HashSet<>();
        }

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(this)
                .build();

        this.memberChatroomMappingSet.add(memberChatroomMapping);

        return memberChatroomMapping;
    }
}

