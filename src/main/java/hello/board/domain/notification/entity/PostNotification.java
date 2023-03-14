package hello.board.domain.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post ownerPost;

    public PostNotification(String writer, Member member, Post post) {
        inputInfo(post.getTitle(), writer, member);
        this.ownerPost = post;
        member.getNotifications().add(this);
    }

}
