package org.oz.chatservice.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.oz.chatservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatroomId(Long chatroomId);

    Boolean existsByChatroomIdAndCreatedAtAfter(Long chatroomdId, LocalDateTime createdAt);
}
