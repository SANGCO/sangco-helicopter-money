package dev.sangco.hm.web;

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

    @Test
    public void createRandomTransferTest() throws Exception {
        RandomTransferRequestDto requestDto = RandomTransferRequestDto.builder()
                .totalCount(5)
                .totalAmount("10000")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-USER-ID", "5128016860359238732");
        httpHeaders.add("X-ROOM-ID", "ce344d92da9d4cf0b57f956558fc17a4");

        HttpEntity<RandomTransferRequestDto> httpEntity = new HttpEntity<>(requestDto, httpHeaders);

        ResponseEntity<ResponseEntity> responseEntity = template
                .exchange("/random/transfers", POST, httpEntity, ResponseEntity.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

}