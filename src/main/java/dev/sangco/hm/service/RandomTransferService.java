package dev.sangco.hm.service;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.repository.GroupChatRepository;
import dev.sangco.hm.repository.MemberRepository;
import dev.sangco.hm.repository.RandomTransferRepository;
import dev.sangco.hm.web.dto.RandomTransferRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RandomTransferService {

    private final RandomTransferRepository randomTransferRepository;
    private final MemberRepository memberRepository;
    private final GroupChatRepository groupChatRepository;

    @Transactional
    public Long saveRandomTransfer(RandomTransferRequestDto requestDto) {
        Member member = memberRepository.findByExternalId(requestDto.getXUserId())
                .orElseThrow(IllegalArgumentException::new);
        GroupChat groupChat = groupChatRepository.findByExternalId(requestDto.getXRoomId())
                .orElseThrow(IllegalArgumentException::new);

        RandomTransfer randomTransfer = RandomTransfer.builder()
                .member(member)
                .groupChat(groupChat)
                .totalCount(requestDto.getTotalCount())
                .totalAmount(requestDto.getTotalAmount())
                .build();

        String token = randomTransfer.generateToken();
        if (randomTransferRepository.existsByMemberAndGroupChatAndToken(
                member, groupChat, token)) {
            throw new IllegalStateException();
        }

        randomTransfer.setToken(token);
        RandomTransfer savedRandomTransfer = randomTransferRepository.save(randomTransfer);
        return savedRandomTransfer.getId();
    }

    public Optional<RandomTransfer> findOne(Long randomTransferId) {
        return randomTransferRepository.findById(randomTransferId);
    }

}
