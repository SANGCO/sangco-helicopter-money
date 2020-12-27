package dev.sangco.hm.service;

import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.repository.RandomTransferRepository;
import dev.sangco.hm.web.dto.RandomTransferRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RandomTransferService {

    private final RandomTransferRepository randomTransferRepository;

    @Transactional
    public Long saveRandomTransfer(RandomTransferRequestDto requestDto) {


//        new RandomTransfer(requestDto.getTotalCount(), new BigDecimal(requestDto.getTotalAmount()))


//        RandomTransfer savedRandomTransfer = randomTransferRepository.save();
//        return savedRandomTransfer.getId();
        return 0L;
    }


    public RandomTransfer findOne(Long randomTransferId) {
        return randomTransferRepository.getOne(randomTransferId);
    }

}
