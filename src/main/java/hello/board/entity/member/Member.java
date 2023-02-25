package hello.board.entity.member;

import hello.board.controller.dto.req.MemberReqDto;
import hello.board.entity.base.BaseTimeEntity;
import hello.board.entity.post.Post;
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

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(String name, int age, String loginId, String password) {
        this.name = name;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
    }

    public Member(MemberReqDto memberDto) {
        this.name = memberDto.getName();
        this.age = memberDto.getAge();
        this.loginId = memberDto.getLoginId();
        this.password = memberDto.getPassword();
    }

    //== 업데이트 로직 ==//
    public void updateInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
