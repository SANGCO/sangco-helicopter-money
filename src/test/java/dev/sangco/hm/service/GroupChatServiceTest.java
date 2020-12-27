package dev.sangco.hm.service;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GroupChatServiceTest {

    @Autowired
    GroupChatService groupChatService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void saveGroupChatTest() {
        // Given
        List<Member> members = memberRepository.findAll();

        // When
        long groupChatId = groupChatService.saveGroupChat(members);
        GroupChat groupChat = groupChatService.findOne(groupChatId);

        // Then
        assertThat(groupChat.getMembers().size()).isEqualTo(10);
        assertThat(groupChat.getExternalId()).isNotNull();
    }

}