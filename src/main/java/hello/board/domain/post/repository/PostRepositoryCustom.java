package hello.board.domain.post.repository;

import hello.board.controller.post.dto.req.PostSearchCondition;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

    Optional<Post> findPostWithMemberInfo(Long postId);

    Optional<Post> findPostWithCommentInfo(Long postId);

    List<Post> findPostsOfMember(Long memberId);

    Page<PostResDto> search(PostSearchCondition condition, Pageable pageable);

    Page<PostResDto> findAllPostedPost(Pageable pageable);

    Page<PostResDto> findAllAllWaitingPost(Pageable pageable);
}
