package hello.board.controller.member.dto.res;

import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResDto {

    private Long id;
    private String name;
    private int age;
    private MemberRole role;

    public MemberResDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
        this.role = member.getRole();
    }
}
