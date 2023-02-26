package hello.board.domain.member.entity;

import hello.board.controller.member.dto.req.MemberReqDto;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.post.entity.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(String name, int age, String loginId, String password, MemberRole role) {
        this.name = name;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }

    public Member(MemberReqDto memberDto) {
        this.name = memberDto.getName();
        this.age = memberDto.getAge();
        this.loginId = memberDto.getLoginId();
        this.password = memberDto.getPassword();
        this.role = memberDto.getRole();
    }

    //== 업데이트 로직 ==//
    public void updateInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
