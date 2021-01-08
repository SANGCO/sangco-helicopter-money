package dev.sangco.hm.repository;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.domain.RandomTransfer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    private RandomTransfer savedRandomTransfer;

    @Before
    public void setup() {
        List<Member> members = memberRepository.findAll();
        Member member = members.get(0);
        List<GroupChat> groupChats = groupChatRepository.findAll();
        GroupChat groupChat = groupChats.get(0);
        RandomTransfer randomTransfer = RandomTransfer.builder()
                .member(member)
                .groupChat(groupChat)
                .totalCount(5)
                .totalAmount("100000")
                .build();
        randomTransfer.setToken(randomTransfer.generateToken());
        savedRandomTransfer = randomTransferRepository.save(randomTransfer);
    }

    @Test
    public void existsByMemberAndGroupChatAndTokenTest() {
        // Given

        // When
        boolean b = randomTransferRepository.existsByMemberAndGroupChatAndToken(
                        savedRandomTransfer.getMember(),
                        savedRandomTransfer.getGroupChat(),
                        savedRandomTransfer.getToken());

        // Then
        assertThat(b).isTrue();
    }

    @Test
    public void existsByGroupChatAndTokenTest() {
        // Given

        // When
        boolean b = randomTransferRepository.existsByGroupChatAndToken(
                savedRandomTransfer.getGroupChat(),
                savedRandomTransfer.getToken());

        // Then
        assertThat(b).isTrue();
    }

    @Test
    public void findByMemberAndGroupChatAndTokenTest() {
        // Given

        // When
        Optional<RandomTransfer> randomTransfer = randomTransferRepository.findByMemberAndGroupChatAndToken(
                savedRandomTransfer.getMember(),
                savedRandomTransfer.getGroupChat(),
                savedRandomTransfer.getToken());

        // Then
        assertThat(randomTransfer.isPresent()).isTrue();
        RandomTransfer randomTransferObj = randomTransfer.get();
        assertThat(randomTransferObj.getMember()).isEqualTo(savedRandomTransfer.getMember());
        assertThat(randomTransferObj.getGroupChat()).isEqualTo(savedRandomTransfer.getGroupChat());
        assertThat(randomTransferObj.getToken()).isEqualTo(savedRandomTransfer.getToken());
    }

    @Test
    public void findByGroupChatAndTokenTest() {
        // Given

        // When
        Optional<RandomTransfer> randomTransfer = randomTransferRepository.findByGroupChatAndToken(
                savedRandomTransfer.getGroupChat(),
                savedRandomTransfer.getToken());

        // Then
        assertThat(randomTransfer.isPresent()).isTrue();
        RandomTransfer randomTransferObj = randomTransfer.get();
        assertThat(randomTransferObj.getMember()).isEqualTo(savedRandomTransfer.getMember());
        assertThat(randomTransferObj.getGroupChat()).isEqualTo(savedRandomTransfer.getGroupChat());
        assertThat(randomTransferObj.getToken()).isEqualTo(savedRandomTransfer.getToken());
    }

    @Test
    public void findByMemberAndGroupChatAndTokenAndCreatedDateAfterTest() {
        // Given

        // When
        LocalDateTime dateTime = LocalDateTime.now().minusDays(7);
        System.out.println("LocalDateTime : " + dateTime);
        Optional<RandomTransfer> randomTransfer = randomTransferRepository.findByMemberAndGroupChatAndTokenAndCreatedDateAfter(
                savedRandomTransfer.getMember(),
                savedRandomTransfer.getGroupChat(),
                savedRandomTransfer.getToken(),
                dateTime);

        // Then
        assertThat(randomTransfer.isPresent()).isTrue();
        RandomTransfer randomTransferObj = randomTransfer.get();
        assertThat(randomTransferObj.getMember()).isEqualTo(savedRandomTransfer.getMember());
        assertThat(randomTransferObj.getGroupChat()).isEqualTo(savedRandomTransfer.getGroupChat());
        assertThat(randomTransferObj.getToken()).isEqualTo(savedRandomTransfer.getToken());
        assertThat(randomTransferObj.getCreatedDate()).isAfter(dateTime);
    }

}