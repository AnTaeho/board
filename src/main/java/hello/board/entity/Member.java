package hello.board.entity;

import hello.board.entity.base.BaseTimeEntity;
import hello.board.entity.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    private int age;

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(String name, int age, String loginId, String password) {
        this.name = name;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
    }

    //== 업데이트 로직 ==//
    public void updateInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
