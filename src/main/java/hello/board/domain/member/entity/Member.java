package hello.board.domain.member.entity;

import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.post.entity.Post;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    public Member(String name, int age, String loginId, String password, MemberRole role) {
        this.name = name;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }

    //== 업데이트 로직 ==//
    public void updateInfo(MemberUpdateReqDto memberUpdateReqDto) {
        this.name = memberUpdateReqDto.getName();
        this.age = memberUpdateReqDto.getAge();
        this.loginId = memberUpdateReqDto.getLoginId();
    }
}
