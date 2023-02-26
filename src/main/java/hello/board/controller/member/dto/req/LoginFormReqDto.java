package hello.board.controller.member.dto.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginFormReqDto {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    public LoginFormReqDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
