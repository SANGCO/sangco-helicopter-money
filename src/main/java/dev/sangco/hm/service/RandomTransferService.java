package dev.sangco.hm.service;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.domain.RandomTransferReceiver;
import dev.sangco.hm.repository.GroupChatRepository;
import dev.sangco.hm.repository.MemberRepository;
import dev.sangco.hm.repository.RandomTransferRepository;
import dev.sangco.hm.web.dto.RandomTransferRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    public RandomTransfer findOne(RandomTransferRequestDto requestDto) throws Exception {
        Member member = memberRepository.findByExternalId(requestDto.getXUserId())
                .orElseThrow(IllegalArgumentException::new);
        GroupChat groupChat = groupChatRepository.findByExternalId(requestDto.getXRoomId())
                .orElseThrow(IllegalArgumentException::new);

        RandomTransfer randomTransfer = randomTransferRepository
                .findByMemberAndGroupChatAndTokenAndCreatedDateAfter(
                        member, groupChat, requestDto.getXRandomToken(), LocalDateTime.now().minusDays(7))
                .orElseThrow(IllegalArgumentException::new);;
        return randomTransfer;
    }

    public void checkTransferRequest(RandomTransferRequestDto requestDto) throws Exception {
        Member member = memberRepository.findByExternalId(requestDto.getXUserId())
                .orElseThrow(IllegalAccessException::new);
        GroupChat groupChat = groupChatRepository.findByExternalId(requestDto.getXRoomId())
                .orElseThrow(IllegalAccessException::new);
        boolean exist = randomTransferRepository.existsByMemberAndGroupChatAndToken(
                member, groupChat, requestDto.getXRandomToken());
        if (!exist) {
            throw new IllegalAccessException();
        }

        RandomTransfer randomTransfer = randomTransferRepository.findByMemberAndGroupChatAndToken(
                member, groupChat, requestDto.getXRandomToken())
                .orElseThrow(IllegalAccessException::new);
        if (randomTransfer.getMember().equals(member)) {
            throw new IllegalAccessException();
        }

        // TODO RandomTransfer로 옮기는게 좋을거 같은데
        List<RandomTransferReceiver> receivers = randomTransfer.getReceivers();
        for (RandomTransferReceiver randomTransferReceiver : receivers) {
            if (randomTransferReceiver.getMember().equals(member)) {
                throw new IllegalAccessException();
            }
        }
    }

    @Transactional
    public RandomTransferReceiver transferOne(RandomTransferRequestDto requestDto) throws Exception {
        Member member = memberRepository.findByExternalId(requestDto.getXUserId())
                .orElseThrow(IllegalAccessException::new);
        GroupChat groupChat = groupChatRepository.findByExternalId(requestDto.getXRoomId())
                .orElseThrow(IllegalAccessException::new);
        RandomTransfer randomTransfer = randomTransferRepository
                .findByGroupChatAndToken(groupChat, requestDto.getXRandomToken())
                .orElseThrow(IllegalAccessException::new);
        // TODO 여기서 isActive 부터 체크해야 할거 같은데

        if (LocalDateTime.now().isAfter(randomTransfer.getCreatedDate().plusMinutes(10L))) {
            randomTransfer.setActive(false);
            // TODO 익셉션 던지면 이거 저장이 되나?
            throw new IllegalAccessException();
        }

        RandomTransferReceiver randomTransferReceiver = null;
        for (RandomTransferReceiver r : randomTransfer.getReceivers()) {
            if (!r.getIsDone()) {
                r.setDone(true);
                r.setMember(member);
                randomTransferReceiver = r;
                break;
            }
        }

        if (randomTransferReceiver == null) {
            randomTransfer.setActive(false);
            // TODO 여기도 이거 저장되나?
            throw new IllegalAccessException();
        }

        return randomTransferReceiver;
    }

}