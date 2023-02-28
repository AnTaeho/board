package hello.board.controller.member.dto.req;

import hello.board.domain.member.entity.MemberRole;
import lombok.*;

@Data
public class MemberRegisterReqDto {

    private String name;
    private int age;
    private String loginId;
    private String password;
    private MemberRole role;
}
