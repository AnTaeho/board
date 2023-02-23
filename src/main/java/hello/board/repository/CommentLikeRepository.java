package hello.board.repository;

import hello.board.entity.comment.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
