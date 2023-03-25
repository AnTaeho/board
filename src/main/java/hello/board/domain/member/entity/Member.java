package hello.board.domain.member.entity;

import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.post.entity.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    private String loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "commentMember", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "notifiedMember", cascade = CascadeType.REMOVE)
    private List<Notification> notifications = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Member createMember(MemberRegisterReqDto memberRegisterReqDto) {
        return Member.builder()
                .name(memberRegisterReqDto.getName())
                .age(memberRegisterReqDto.getAge())
                .loginId(memberRegisterReqDto.getLoginId())
                .password(memberRegisterReqDto.getPassword())
                .role(memberRegisterReqDto.getRole())
                .build();
    }

    //== 업데이트 로직 ==//
    public void updateInfo(MemberUpdateReqDto memberUpdateReqDto) {
        this.name = memberUpdateReqDto.getName();
        this.age = memberUpdateReqDto.getAge();
        this.loginId = memberUpdateReqDto.getLoginId();
    }

}
