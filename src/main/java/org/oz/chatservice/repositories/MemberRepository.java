package org.oz.chatservice.repositories;

import java.util.Optional;
import org.oz.chatservice.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
