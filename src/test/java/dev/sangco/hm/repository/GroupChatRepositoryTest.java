package dev.sangco.hm.repository;

import dev.sangco.hm.domain.GroupChat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GroupChatRepositoryTest {

    @Autowired
    GroupChatRepository groupChatRepository;

    @Test
    public void findByExternalIdTest() {
        // Given
        GroupChat groupChat = new GroupChat();
        GroupChat savedGroupChat = groupChatRepository.save(groupChat);
        Optional<GroupChat> findGroupChat = groupChatRepository.findByExternalId(savedGroupChat.getExternalId());

        // When

        // Then
        assertThat(findGroupChat.isPresent()).isTrue();
    }

}