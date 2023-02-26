package hello.board.controller.post.dto.res;

import hello.board.domain.post.entity.Post;
import lombok.Data;

import javax.persistence.Lob;

@Data
public class PostResDto {

    private Long id;
    private String title;

    @Lob
    private String content;

    public PostResDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
