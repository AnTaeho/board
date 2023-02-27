package hello.board.domain.member.entity;

import hello.board.controller.member.dto.req.MemberReqDto;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static Member from(MemberReqDto memberDto) {
        return new Member(memberDto.getName(),
                memberDto.getAge(),
                memberDto.getLoginId(),
                memberDto.getPassword(),
                memberDto.getRole());
    }

    //== 업데이트 로직 ==//
    public void updateInfo(Member updateMember) {
        this.name = updateMember.getName();
        this.age = updateMember.getAge();
        this.loginId = updateMember.getLoginId();
    }
}
