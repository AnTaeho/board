package hello.board.domain.notification.entity;

import hello.board.domain.member.entity.Member;
import hello.board.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@DiscriminatorValue("P")
@NoArgsConstructor
public class PostNotification extends Notification{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post ownerPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member notifiedMember;

    public PostNotification(String writer, Member member, Post post) {
        inputInfo(post.getTitle(), writer);
        this.notifiedMember = member;
        this.ownerPost = post;
    }

}
