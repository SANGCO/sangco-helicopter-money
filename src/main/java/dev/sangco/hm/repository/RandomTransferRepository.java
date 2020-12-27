package dev.sangco.hm.repository;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.domain.RandomTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomTransferRepository extends JpaRepository<RandomTransfer, Long> {

    boolean existsByMemberAndGroupChatAndToken(Member member, GroupChat groupChat, String token);

}
