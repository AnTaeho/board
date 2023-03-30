package hello.board.controller.notification.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class NotificationUpdateReqDto {

    @NotNull(message = "내용을 작성해 주세요.")
    private String content;
}
