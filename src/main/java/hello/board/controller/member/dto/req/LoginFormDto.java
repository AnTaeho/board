package hello.board.controller.member.dto.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginFormDto {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    public LoginFormDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
