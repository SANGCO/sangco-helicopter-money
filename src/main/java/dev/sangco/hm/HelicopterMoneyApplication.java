package dev.sangco.hm;

import dev.sangco.hm.domain.Member;
import dev.sangco.hm.repository.MemberRepository;
import dev.sangco.hm.service.GroupChatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaAuditing
public class HelicopterMoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelicopterMoneyApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(MemberRepository memberRepository,
									GroupChatService groupChatService) {
		return (args -> {
			IntStream.rangeClosed(1, 10).forEach(i -> {
				Member savedMember = memberRepository
						.save(new Member("initData" + i, "10000" + i));
			});

			List<Member> members = memberRepository.findAll();
			groupChatService.saveGroupChat(members);
		});
	}

}
