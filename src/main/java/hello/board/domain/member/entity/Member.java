package hello.board.domain.member.entity;

import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    private String loginId;
    private String password;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member parent;

//    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Member> followers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "notifiedMember")
    private List<Notification> notifications = new ArrayList<>();

    //== 업데이트 로직 ==//
    public void updateInfo(MemberUpdateReqDto memberUpdateReqDto) {
        this.name = memberUpdateReqDto.getName();
        this.age = memberUpdateReqDto.getAge();
        this.loginId = memberUpdateReqDto.getLoginId();
    }

    public void addFollower(Member followMember) {
        this.followers.add(followMember);
    }

    public void deleteFollower(Member followMember) {
        this.followers.remove(followMember);
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }
}
