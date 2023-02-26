package hello.board.controller.member.dto.res;

import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import lombok.Data;

@Data
public class MemberResDto {

    private Long id;
    private String name;
    private int age;

    private String loginId;
    private String password;

    private MemberRole role;

    public MemberResDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.role = member.getRole();
    }
}
