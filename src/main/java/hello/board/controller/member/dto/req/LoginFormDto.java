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

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
