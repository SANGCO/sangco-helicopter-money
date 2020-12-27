package dev.sangco.hm.service;

import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.web.dto.RandomTransferRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RandomTransferServiceTest {

    @Autowired
    RandomTransferService randomTransferService;

    @Test
    public void saveRandomTransferTest() {
        // Given

        // When

        // Then

        RandomTransferRequestDto requestDto = RandomTransferRequestDto.builder()
                .totalCount(5)
                .totalAmount("10000")
                .build();
        Long id = randomTransferService.saveRandomTransfer(requestDto);
        RandomTransfer randomTransfer = randomTransferService.findOne(id);
        assertThat(randomTransfer.getReceivers().size()).isEqualTo(5);
    }

}