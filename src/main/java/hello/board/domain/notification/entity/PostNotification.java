package hello.board.domain.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.board.domain.member.entity.Member;
import hello.board.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("P")
@NoArgsConstructor
public class PostNotification extends Notification{

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post ownerPost;

    private PostNotification(String writer, Member member, Post post) {
        inputInfo(post.getTitle(), writer, member);
        this.ownerPost = post;
        member.getNotifications().add(this);
    }

    public static PostNotification from(String writer, Member member, Post post) {
        return new PostNotification(writer, member, post);
    }

}
