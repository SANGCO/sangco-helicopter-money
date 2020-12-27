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
        Member member1 = new Member("test1");
        Member member2 = new Member("test2");
        Member member3 = new Member("test3");
        Member member4 = new Member("test4");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        List<Long> ids = Arrays.asList(
                member1.getId(), member2.getId(), member3.getId(), member4.getId());
        long groupChatId = groupChatService.saveGroupChat(ids);
        GroupChat groupChat = groupChatService.findOne(groupChatId);

        assertThat(groupChat.getMembers().size()).isEqualTo(4);
    }

}