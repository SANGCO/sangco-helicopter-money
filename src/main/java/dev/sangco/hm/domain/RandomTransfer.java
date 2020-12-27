package dev.sangco.hm.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RandomTransfer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "random_transfer_id")
    private Long id;

    // TODO 토큰 생성 로직
    @Column(unique = true)
    private String token;

    private int totalCount;

    private BigDecimal totalAmount;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "random_transfer_id")
    private List<RandomTransferReceiver> receivers = new ArrayList<>();

    public RandomTransfer(int totalCount, BigDecimal totalAmount) {
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
        addRecievers();
    }

    // TODO 분배 로직
    public void addRecievers() {
        for (int i = 1; i <= totalCount; i++) {
            receivers.add(new RandomTransferReceiver(
                    totalAmount.divide(new BigDecimal(totalCount))));
        }
    }

}
