package hello.board.controller.member.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormDto {

    @NotEmpty(message = "로그인 아이디를 입력해 주세요.")
    private String loginId;

    @NotEmpty(message = "로그인 비밀번호를 입력해주세요")
    private String password;

}
