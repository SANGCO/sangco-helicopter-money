package dev.sangco.hm.domain;

import com.fasterxml.uuid.Generators;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
public class GroupChat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_chat_id")
    private Long id;

    @Column(unique = true)
    private String externalId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_chat_member",
            joinColumns = @JoinColumn(name = "group_chat_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Member> members = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "group_chat_id")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public GroupChat() {
        this.externalId = Generators.timeBasedGenerator()
                .generate().toString().replace("-", "");
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void addMembers(List<Member> members) {
        this.members.addAll(members);
    }

    public void addChatMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }

}
