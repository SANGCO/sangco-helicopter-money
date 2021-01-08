package dev.sangco.hm.service;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.domain.RandomTransferReceiver;
import dev.sangco.hm.repository.GroupChatRepository;
import dev.sangco.hm.repository.MemberRepository;
import dev.sangco.hm.web.dto.RandomTransferRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RandomTransferServiceTest {

    @Autowired
    RandomTransferService randomTransferService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GroupChatRepository groupChatRepository;

    private Optional<RandomTransfer> randomTransfer;

    @Before
    public void setup() {
        List<Member> members = memberRepository.findAll();
        List<GroupChat> groupChats = groupChatRepository.findAll();

        RandomTransferRequestDto requestDto = RandomTransferRequestDto.builder()
            .totalCount(5)
            .totalAmount("10000")
            .build();
        requestDto.addHeaders(members.get(0).getExternalId(),
                groupChats.get(0).getExternalId());
        Long id = randomTransferService.saveRandomTransfer(requestDto);
        randomTransfer = randomTransferService.findOne(id);
    }

    @Test
    public void saveRandomTransferTest() {
        // Given

        // When

        // Then
        assertThat(randomTransfer.isPresent()).isTrue();
        assertThat(randomTransfer.get().getReceivers().size()).isEqualTo(5);
    }

    @Test
    public void addRecieversTest() {
        // Given

        // When
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RandomTransferReceiver r : randomTransfer.get().getReceivers()) {
            totalAmount = totalAmount.add(r.getAmount());
        }

        // Then
        assertThat(randomTransfer.get().getTotalAmount()).isEqualTo(totalAmount);
    }

    @Test
    public void transferOneTest() throws Exception {
        // Given

        // When
        RandomTransfer randomTransfer = this.randomTransfer.get();
        for (RandomTransferReceiver receiver : randomTransfer.getReceivers()) {
            receiver.setDone(true);
            receiver.setMember(randomTransfer.getMember());
        }

        RandomTransferRequestDto requestDto = RandomTransferRequestDto.builder()
                .xUserId(randomTransfer.getMember().getExternalId())
                .xRoomId(randomTransfer.getGroupChat().getExternalId())
                .xRandomToken(randomTransfer.getToken()).build();

        // Then
        try {
            randomTransferService.transferOne(requestDto);
        } catch (IllegalAccessException ex) {
            assertThat(randomTransfer.getIsActive()).isFalse();
        }
    }

}