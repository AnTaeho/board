package hello.board.controller.member.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberUpdateReqDto {

    private String name;
    private int age;
    private String loginId;

}
