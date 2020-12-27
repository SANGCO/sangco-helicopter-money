package dev.sangco.hm.repository;

import dev.sangco.hm.domain.Account;
import dev.sangco.hm.domain.CurrencyCode;
import dev.sangco.hm.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    private Member member;
    private Member savedMember;
    private Optional<Member> findMember;

    @Before
    public void setup() {
        member = new Member("test1234", "100000");
        savedMember = memberRepository.save(member);
        findMember = memberRepository.findById(savedMember.getId());
    }

    @Test
    public void testMember() {
        // Given

        // When

        // Then
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
        assertThat(findMember.get()).isEqualTo(member);
        assertThat(findMember.get().getAccount()).isEqualTo(member.getAccount());
        assertThat(member.getAccount().getAmount()).isEqualTo(new BigDecimal("100000"));
    }

    @Test
    public void member_exteralId_생성하는_부분_테스트() {
        // Given

        // When
        member.generateExternalId();

        // Then
        assertThat(findMember.get().getExternalId()).isEqualTo(member.getExternalId());
    }

}