package hello.board.domain.post.repository;

import hello.board.domain.post.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @EntityGraph(attributePaths = {"member", "comments"})
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findWithMemberAndCommentByPostId(@Param("postId") Long postId);

    @EntityGraph(attributePaths = {"comments"})
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findWithCommentByPostId(@Param("postId")Long postId);

    @EntityGraph(attributePaths = {"member", "comments"})
    @Query("select p from Post p where p.member.id = :memberId")
    List<Post> findPostsByMemberId(@Param("memberId") Long memberId);
}
