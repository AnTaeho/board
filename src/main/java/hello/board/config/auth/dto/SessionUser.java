package hello.board.config.auth.dto;

import hello.board.domain.member.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private final String name;
    private final int age;
    private final String email;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.age = member.getAge();
        this.email = member.getEmail();
    }
}
