package dev.sangco.hm;

import dev.sangco.hm.domain.Member;
import dev.sangco.hm.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaAuditing
public class HelicopterMoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelicopterMoneyApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(MemberRepository memberRepository) {
		return (args -> {
			IntStream.rangeClosed(1, 10).forEach(i -> {
				Member savedMember = memberRepository
						.save(new Member("initData" + i, "10000" + i));
				savedMember.generateExternalId();
				memberRepository.save(savedMember);
			});
		});
	}

}
