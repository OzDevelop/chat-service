package org.oz.chatservice.repositories;

import org.oz.chatservice.entities.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long > {

}
