package dev.sangco.hm.repository;

import dev.sangco.hm.domain.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {

    Optional<GroupChat> findByExternalId(String externalId);

}
