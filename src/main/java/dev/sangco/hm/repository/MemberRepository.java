package dev.sangco.hm.repository;

import dev.sangco.hm.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
