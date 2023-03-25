package hello.board.controller.member.dto.res;

import hello.board.controller.notification.dto.res.NotificationResDto;
import hello.board.controller.post.dto.res.*;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllMemberInfoDto {

    private String name;
    private int age;
    private String loginId;
    private String password;
    private MemberRole role;

    private List<PostResDto> postResDto = new ArrayList<>();

    private List<NotificationResDto> notificationResDto = new ArrayList<>();

    public AllMemberInfoDto(Member member) {
        this.name = member.getName();
        this.age = member.getAge();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.role = member.getRole();
        makePostForm(member);
        makeNotificationForm(member);
    }

    private void makePostForm(Member member) {
        for (Post post : member.getPosts()) {
            postResDto.add(new PostResDto(post));
        }
    }

    private void makeNotificationForm(Member member) {
        for (Notification notification : member.getNotifications()) {
            notificationResDto.add(new NotificationResDto(notification));
        }
    }
}
