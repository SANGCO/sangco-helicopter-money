package dev.sangco.hm.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CurrencyCode code = CurrencyCode.KRW;

    private BigDecimal amount;

    public Account(String amount) {
        this.amount = new BigDecimal(amount);
    }
}
