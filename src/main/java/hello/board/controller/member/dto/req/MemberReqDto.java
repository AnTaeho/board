package hello.board.controller.member.dto.req;

import hello.board.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberReqDto {

    private Long id;
    private String name;
    private int age;
    private String loginId;
    private String password;
    private MemberRole role;

}
