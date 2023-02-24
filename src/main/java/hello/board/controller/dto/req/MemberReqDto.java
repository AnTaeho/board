package hello.board.controller.dto.req;

import lombok.Data;

@Data
public class MemberReqDto {

    private String name;
    private int age;

    private String loginId;
    private String password;

}
