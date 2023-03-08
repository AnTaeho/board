package hello.board.controller.member.dto.req;

import hello.board.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterReqDto {

    @NotEmpty
    private String name;

    @NotNull
    private int age;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotNull
    private MemberRole role;

}
