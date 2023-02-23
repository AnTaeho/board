package hello.board.entity.comment;

import hello.board.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentLike(Member findMember, Comment findComment) {
        this.member = findMember;
        this.comment = findComment;
    }
}
