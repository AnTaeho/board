package hello.board.controller.dto.res;

import hello.board.entity.Member;
import lombok.Data;

@Data
public class MemberResDto {

    private String name;
    private int age;

    private String loginId;
    private String password;

    public MemberResDto(Member member) {
        this.name = member.getName();
        this.age = member.getAge();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
    }
}
