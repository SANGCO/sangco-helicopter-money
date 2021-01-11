package dev.sangco.hm.web.dto;

import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.domain.RandomTransferReceiver;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class RandomTransferResponseDto {

    private String dateTime;

    private String totalAmount;

    private String receivedAmount;

    private List<RandomTransferReceiverResponseDto> receivers = new ArrayList<>();

    public RandomTransferResponseDto(RandomTransfer randomTransfer) {
        this.dateTime = randomTransfer.getCreatedDate().toString();
        this.totalAmount = randomTransfer.getTotalAmount().toString();
        BigDecimal temp = new BigDecimal("0");
        for (RandomTransferReceiver receiver : randomTransfer.getReceivers()) {
            if (receiver.getIsDone()) {
                receivers.add(new RandomTransferReceiverResponseDto(receiver));
                temp = temp.add(receiver.getAmount());
            }
        }

        receivedAmount = temp.toString();
    }
}

