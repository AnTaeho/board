package hello.board.controller.notification.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class NotificationUpdateReqDto {

    @NotNull
    private String content;
}
