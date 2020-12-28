package dev.sangco.hm.repository;

import dev.sangco.hm.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByExternalId(Long externalId);

}
