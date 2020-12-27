package dev.sangco.hm.service;

import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.repository.RandomTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RandomTransferService {

    private final RandomTransferRepository randomTransferRepository;

    public Long saveRandomTransfer(int totalCount, BigDecimal totalAmount) {
        RandomTransfer savedRandomTransfer = randomTransferRepository.save(
                new RandomTransfer(totalCount, totalAmount));
        return savedRandomTransfer.getId();
    }

    public RandomTransfer findOne(Long randomTransferId) {
        return randomTransferRepository.getOne(randomTransferId);
    }

}
