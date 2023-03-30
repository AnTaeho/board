package hello.board.controller.member.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateReqDto {

    @NotNull(message = "수정할 이름을 입력해 주세요.")
    private String name;

    @NotNull(message = "수정할 나이를 입력해 주세요.")
    private int age;

    @NotNull(message = "수정할 ID를 입력해 주세요.")
    private String loginId;

}
