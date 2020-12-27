package dev.sangco.hm.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;

//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RandomTransferReceiverHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "random_transfer_receiver_history_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    private BigDecimal amount;

    public RandomTransferReceiverHistory(RandomTransferReceiver randomTransferReceiver) {
        this.member = randomTransferReceiver.getMember();
        this.amount = randomTransferReceiver.getAmount();
    }
}
