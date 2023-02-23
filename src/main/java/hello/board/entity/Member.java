package hello.board.entity;

import hello.board.entity.base.BaseTimeEntity;
import hello.board.entity.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
