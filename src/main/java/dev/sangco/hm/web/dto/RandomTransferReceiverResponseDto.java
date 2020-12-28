package dev.sangco.hm.web.dto;

import dev.sangco.hm.domain.RandomTransferReceiver;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RandomTransferReceiverResponseDto {

    private String amount;

    private String userId;

    public RandomTransferReceiverResponseDto(RandomTransferReceiver randomTransferReceiver) {
        this.amount = randomTransferReceiver.getAmount().toString();
        this.userId = randomTransferReceiver.getMember().getUserId();
    }
}
