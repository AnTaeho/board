package hello.board.controller.member.dto.res;

import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class MemberRegisterResDto {

    private String name;
    private int age;
    private String loginId;
    private String password;
    private MemberRole role;

    public MemberRegisterResDto(Member member) {
        this.name = member.getName();
        this.age = member.getAge();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.role = member.getRole();
    }
}
