package hello.board.domain.post.repository;

import hello.board.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

    Optional<Post> findPostWithMemberInfo(Long postId);

    Optional<Post> findPostWithCommentInfo(Long postId);

    List<Post> findPostsOfMember(Long memberId);
}
