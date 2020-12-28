package dev.sangco.hm.repository;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.domain.RandomTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Optional;

public interface RandomTransferRepository extends JpaRepository<RandomTransfer, Long> {

    boolean existsByMemberAndGroupChatAndToken(Member member, GroupChat groupChat, String token);

    boolean existsByGroupChatAndToken(GroupChat groupChat, String token);

    Optional<RandomTransfer> findByMemberAndGroupChatAndToken(Member member, GroupChat groupChat, String token);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<RandomTransfer> findByGroupChatAndToken(GroupChat groupChat, String token);

    Optional<RandomTransfer> findByMemberAndGroupChatAndTokenAndCreatedDateAfter(Member member, GroupChat groupChat, String token, LocalDateTime dateTime);

}
