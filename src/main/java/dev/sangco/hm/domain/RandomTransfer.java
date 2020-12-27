package dev.sangco.hm.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RandomTransfer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "random_transfer_id")
    private Long id;

    private Boolean isActive = true;

    @Column(unique = true)
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private Member owner;

    @OneToOne(fetch = FetchType.LAZY)
    private GroupChat groupChat;

    private int totalCount;

    private BigDecimal totalAmount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "random_transfer_id")
    private List<RandomTransferReceiver> receivers = new ArrayList<>();

    public RandomTransfer(Member owner, GroupChat groupChat,
                          int totalCount, BigDecimal totalAmount) {
        this.owner = owner;
        this.groupChat = groupChat;
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
        addRecievers();
    }

    public String generateToken() {
        int[] asciiCode = new int[94];
        Random random = new Random(System.currentTimeMillis());
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 33; i < 127; i++) {
            asciiCode[i - 33] = i;
        }

        for(int i = 0; i < 3; i++) {
            stringBuilder.append((char) asciiCode[random.nextInt(asciiCode.length)]);
        }

        return stringBuilder.toString();
    }

    // TODO 분배 로직
    void addRecievers() {
        for (int i = 1; i <= totalCount; i++) {
            receivers.add(new RandomTransferReceiver(
                    totalAmount.divide(new BigDecimal(totalCount))));
        }
    }

}
