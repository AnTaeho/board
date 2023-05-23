package hello.board.domain.post.repository;

import hello.board.domain.post.entity.Post;
import hello.board.exception.notfound.PostNotFoundException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @EntityGraph(attributePaths = {"member", "comments"})
    @Query("select p from Post p where p.member.id = :memberId")
    List<Post> findPostsByMemberId(@Param("memberId") Long memberId);

    default Post findPost(Long postId) {
        return findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    default Post findWithMemberByPostId(Long postId) {
        return findWithMemberAndCommentByPostId(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    @EntityGraph(attributePaths = {"member", "comments"})
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findWithMemberAndCommentByPostId(@Param("postId") Long postId);

    default Post findPostWithCommentInfo(Long postId) {
        return findWithCommentByPostId(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    @EntityGraph(attributePaths = {"comments"})
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findWithCommentByPostId(@Param("postId")Long postId);
}
