package hello.board.controller.dto;

import hello.board.entity.post.Post;
import lombok.Data;

import javax.persistence.Lob;

@Data
public class PostDto {

    private Long id;
    private String title;

    @Lob
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
