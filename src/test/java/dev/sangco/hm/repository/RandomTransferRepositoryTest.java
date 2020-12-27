package dev.sangco.hm.repository;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.domain.RandomTransfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RandomTransferRepositoryTest {

    @Autowired
    RandomTransferRepository randomTransferRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GroupChatRepository groupChatRepository;

    @Test
    public void existsByMemberAndGroupChatAndTokenTest() {
        // Given
        List<Member> members = memberRepository.findAll();
        List<GroupChat> groupChats = groupChatRepository.findAll();
        RandomTransfer randomTransfer = new RandomTransfer(
                members.get(0), groupChats.get(0), 5, "10000");
        RandomTransfer savedRandomTransfer = randomTransferRepository.save(randomTransfer);

        // When
        boolean b = randomTransferRepository.existsByMemberAndGroupChatAndToken(
                        savedRandomTransfer.getMember(),
                        savedRandomTransfer.getGroupChat(),
                        savedRandomTransfer.getToken());

        // Then
        assertThat(b).isTrue();
    }

}