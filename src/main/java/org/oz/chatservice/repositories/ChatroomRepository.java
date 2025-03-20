package org.oz.chatservice.repositories;

import org.oz.chatservice.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<ChatRoom, Long > {

}
