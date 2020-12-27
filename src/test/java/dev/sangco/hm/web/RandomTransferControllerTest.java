package dev.sangco.hm.web;

import dev.sangco.hm.domain.GroupChat;
import dev.sangco.hm.domain.Member;
import dev.sangco.hm.repository.GroupChatRepository;
import dev.sangco.hm.repository.MemberRepository;
import dev.sangco.hm.service.RandomTransferService;
import dev.sangco.hm.web.dto.RandomTransferRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RandomTransferControllerTest {

    @Autowired
    TestRestTemplate template;

    @Autowired
    RandomTransferService randomTransferService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GroupChatRepository groupChatRepository;

    // TODO 분배 로직 테스트
    @Test
    public void createRandomTransferTest() throws Exception {
        // Given
        List<Member> members = memberRepository.findAll();
        List<GroupChat> groupChats = groupChatRepository.findAll();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-USER-ID", Long.toString(members.get(0).getExternalId()));
        httpHeaders.add("X-ROOM-ID", groupChats.get(0).getExternalId());

        RandomTransferRequestDto requestDto = RandomTransferRequestDto.builder()
                .totalCount(5)
                .totalAmount("10000")
                .build();
        HttpEntity<RandomTransferRequestDto> httpEntity = new HttpEntity<>(requestDto, httpHeaders);

        // When
        ResponseEntity<String> responseEntity = template
                .exchange("/random/transfers", POST, httpEntity, String.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getHeaders().containsKey("X-RANDOM-TOKEN")).isTrue();
    }

}