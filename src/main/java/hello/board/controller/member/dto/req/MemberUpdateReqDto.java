package hello.board.controller.member.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateReqDto {

    private String name;
    private int age;
    private String loginId;

}
