package hello.board.entity;

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
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToOne(mappedBy = "member",fetch = FetchType.LAZY)
    private Comment comment;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
