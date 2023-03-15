package hello.board.domain.post.repository;

import hello.board.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

    Optional<Post> findByIdWithFetchJoinMember(Long postId);

    List<Post> findPostsOfMember(Long memberId);
}
