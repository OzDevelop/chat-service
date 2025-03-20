package org.oz.chatservice.repositories;

import java.util.List;
import org.oz.chatservice.entities.MemberChatroomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatroomMappingRepository extends JpaRepository<MemberChatroomMapping, Long> {
    //Spring Data JPA ㅇㅔ서 제공하는 Query 메서드로 생성
    boolean existsByChatroomIdAndMemberId(Long chatroomId, Long memberId);
    void deleteByChatroomIdAndMemberId(Long chatroomId, Long memberId);
    List<MemberChatroomMapping> findByAllByMemberId(Long memberId);
}
