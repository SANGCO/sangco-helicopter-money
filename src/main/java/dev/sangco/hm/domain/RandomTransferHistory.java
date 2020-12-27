package dev.sangco.hm.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RandomTransferHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "random_transfer_history_id")
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private Member owner;

    private int totalCount;

    private BigDecimal totalAmount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "random_transfer_id")
    private List<RandomTransferReceiver> receivers = new ArrayList<>();

    public RandomTransferHistory(RandomTransfer randomTransfer) {
        this.token = randomTransfer.getToken();
        this.owner = randomTransfer.getMember();
        this.totalCount = randomTransfer.getTotalCount();
        this.totalAmount = randomTransfer.getTotalAmount();
        this.receivers = randomTransfer.getReceivers();
    }

}
