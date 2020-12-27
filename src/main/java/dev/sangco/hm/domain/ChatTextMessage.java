package dev.sangco.hm.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("text") @Getter
public class ChatTextMessage extends ChatMessage {

    private String text;

    public ChatTextMessage(String text) {
        this.text = text;
    }

}
