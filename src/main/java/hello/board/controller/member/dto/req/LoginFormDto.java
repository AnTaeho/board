package hello.board.controller.member.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class LoginFormDto {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
