package dev.sangco.hm.repository;

import dev.sangco.hm.domain.CurrencyCode;
import dev.sangco.hm.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member("test1234");
        Member savedMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(savedMember.getId());

        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
        assertThat(findMember.get()).isEqualTo(member);
    }

}