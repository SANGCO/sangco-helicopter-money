package dev.sangco.hm.repository;

import dev.sangco.hm.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
        // Given
        Member member = new Member("test1234", "100000");
        Member savedMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(savedMember.getId());

        // When

        // Then
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
        assertThat(findMember.get()).isEqualTo(member);
        assertThat(findMember.get().getAccount()).isEqualTo(member.getAccount());
        assertThat(member.getAccount().getAmount()).isEqualTo(new BigDecimal("100000"));
        assertThat(findMember.get().getExternalId()).isEqualTo(member.getExternalId());
    }

}