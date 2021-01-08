package dev.sangco.hm.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private GroupChat groupChat;

    private int totalCount;

    private BigDecimal totalAmount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "random_transfer_id")
    private List<RandomTransferReceiver> receivers = new ArrayList<>();

    @Builder
    public RandomTransfer(Member member, GroupChat groupChat,
                          int totalCount, String totalAmount) {
        this.member = member;
        this.groupChat = groupChat;
        this.totalCount = totalCount;
        this.totalAmount = new BigDecimal(totalAmount);
        addReceivers();
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

    public void setToken(String token) {
        this.token = token;
    }

    void addReceivers() {
        int totalAmount = this.totalAmount.intValue();
        Random random = new Random();
        for (int i = 1; i < totalCount; i++) {
            int temp = random.nextInt(totalAmount + 1);
            totalAmount = totalAmount - temp;
            receivers.add(new RandomTransferReceiver(new BigDecimal(temp)));
        }

        receivers.add(new RandomTransferReceiver(new BigDecimal(totalAmount)));
    }

    public boolean checkReceiversMember(Member member) {
        for (RandomTransferReceiver randomTransferReceiver : receivers) {
            if (randomTransferReceiver.getMember() != null) {
                return !Objects.equals(randomTransferReceiver.getMember(), member);
            }
        }

        return true;
    }

    public boolean isExpired() {
        if (LocalDateTime.now().isAfter(getCreatedDate().plusMinutes(10L))) {
            setActive(false);
            return true;
        }

        return false;
    }

    public Optional<RandomTransferReceiver> spreadOne() {
        for (RandomTransferReceiver r : getReceivers()) {
            if (!r.getIsDone()) {
                r.setDone(true);
                r.setMember(member);
                return Optional.of(r);
            }
        }

        setActive(false);
        return Optional.empty();
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
