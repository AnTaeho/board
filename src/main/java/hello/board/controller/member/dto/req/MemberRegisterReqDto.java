package hello.board.controller.member.dto.req;

import hello.board.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterReqDto {

    @NotBlank(message = "이름은 필수 입니다.")
    private String name;

    @NotNull(message = "나이는 필수 입니다.")
    private int age;

    @NotBlank(message = "로그인 ID는 필수 입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String password;

    @NotNull(message = "권한은 필수 입니다.")
    private MemberRole role;

}
