package dev.sangco.hm.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private Long externalId;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    public Member(String userId, String amount) {
        this.userId = userId;
        this.account = new Account(amount);
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        this.externalId = Math.abs(random.nextLong());
    }

}
