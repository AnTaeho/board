package hello.board.domain.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.board.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("W")
@Getter
@NoArgsConstructor
public class WaitingPostNotification extends Notification{

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post ownerPost;

    private WaitingPostNotification(Post post) {
        inputInfo("This post has forbiddenWord, fix the post", "system", post.getMember());
        this.ownerPost = post;
    }

    public static WaitingPostNotification from(Post post) {
        return new WaitingPostNotification(post);
    }
}
