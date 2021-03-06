package dev.sangco.hm.service;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.repository.GroupChatRepository;
import dev.sangco.hm.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupChatService {

    private final GroupChatRepository groupChatRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveGroupChat(List<Member> members) {
        GroupChat groupChat = new GroupChat();
        groupChat.addMembers(members);
        GroupChat savedGroupChat = groupChatRepository.save(groupChat);
        return savedGroupChat.getId();
    }

    public GroupChat findOne(Long groupChatId) {
        return groupChatRepository.getOne(groupChatId);
    }

}
