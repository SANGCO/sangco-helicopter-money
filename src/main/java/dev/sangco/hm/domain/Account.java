package dev.sangco.hm.domain;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private CurrencyCode code = CurrencyCode.KRW;

    private BigDecimal amount = new BigDecimal("0");

}
