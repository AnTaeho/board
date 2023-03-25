package hello.board.controller.member.dto.res;

import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberUpdateResDto {

    private String name;
    private int age;
    private String loginId;
    private MemberRole role;

    public MemberUpdateResDto(Member member) {
        this.name = member.getName();
        this.age = member.getAge();
        this.loginId = member.getLoginId();
        this.role = member.getRole();
    }
}
