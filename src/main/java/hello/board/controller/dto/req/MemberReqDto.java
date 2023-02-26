package hello.board.controller.dto.req;

import hello.board.entity.member.MemberRole;
import lombok.Data;

@Data
public class MemberReqDto {

    private String name;
    private int age;
    private String loginId;
    private String password;
    private MemberRole role;
}
